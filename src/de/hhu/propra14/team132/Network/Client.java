package de.hhu.propra14.team132.Network;

import java.io.*;
import java.net.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import de.hhu.propra14.team132.gameSystem.Communicable;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.Message;

/**
 * 
 * @author <a email-to="Sebastian.Sura@uni-duesseldorf.de">Sebastian Sura</a>
 *
 */
public class Client implements Communicable {
	/*
	 * This class does the following:
	 * 1) Create a 'FutureTask' with a parameter of a 'ClientHandler' instance
	 * 2) Give this 'FutureTask' to another thread. In this thread call the 'call' function of 'ClientHandler'
	 * 3) In that thread the whole communication takes place.
	 */
	 
	 public static void main(String[] args) {	
		try {
			Client client = new Client(null, args[0], 3141);
			String in = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			do {
				in = reader.readLine();
				client.receiveMessage(new ExampleNetworkMessage(in));
			} while (!in.equals("exit"));
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	 }
	 
	 
	 private final Thread sendThread;
	 private final Thread receiveThread;
	 private final Queue<NetworkMessage> messageInBuffer;
	 private final Queue<NetworkMessage> messageOutBuffer;
	 private Socket server;
	 private int clientID;
	 
	 /**
	  * Creates a client that tries to connect to a server.
	  * 
	  * @param g The {@link GameManager} that started the game
	  * @param serverIP The IP-adress of the server you want to connect to
	  * @param port The port the server listens to
	  * @throws IOException
	  * @see Server
	  */
	 public Client(GameManager g, String serverIP, int port) throws IOException {
		this.messageInBuffer		= new ConcurrentLinkedQueue<NetworkMessage>();
		this.messageOutBuffer		= new ConcurrentLinkedQueue<NetworkMessage>();
		this.server 			= new Socket(serverIP, port);
		this.sendThread 		= new Thread(new Sender(server, messageOutBuffer));
		this.receiveThread 		= new Thread(new Receiver(server, messageInBuffer));
		try {
			this.clientID		= getClientID();
			System.out.println("My ID : " + this.clientID);
		} catch (IOException e) {
			// this should not happen
			e.printStackTrace();
			this.clientID		= -1;
		}
		
		if (this.clientID != -1) {
			this.sendThread.start();
			this.receiveThread.start();
		}
	 } 
	 
	 /**
	  * Closes the connection.
	  * 
	  * @throws IOException
	  */
	 public void close() throws IOException {
		 if (server != null) {
			 server.close();
		 }
		 if (sendThread != null) {
			 sendThread.interrupt();
		 }
		 if (receiveThread != null) {
			 receiveThread.interrupt();
		 }
	 }
	 
	 /**
	  * Sends the message over the network.
	  * 
	  * @param message The {@link Message} to send.
	  */
	 public void receiveMessage(Message message) {
		 NetworkMessage netMessage = new NetworkMessage(message, this.clientID);
		 synchronized (this.messageOutBuffer) {
			 if (this.messageOutBuffer.offer(netMessage)) {
				 this.messageOutBuffer.notifyAll();
			 }
		 }
	 }
	 
	@Override
	public void register(GameManager gameManager) {
		// TODO Auto-generated method stub
	}
	 
	 private int getClientID() throws IOException {
		ObjectInputStream in = new ObjectInputStream(this.server.getInputStream());
		Integer clientID = in.readInt();
		System.out.println("Got ClientID : " + clientID);
		return clientID;
	}
	 
	 private class Sender implements Runnable {
		 private final Socket server;
		 private final Queue<NetworkMessage> outBuffer;
		 
		 public Sender(Socket server, Queue<NetworkMessage> outBuffer) {
			 this.server	= server;
			 this.outBuffer	= outBuffer;
		 }
		 
		 public void run() {
			 try {
				ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(server.getOutputStream()));
				while (true) {
					 synchronized(outBuffer) {
						 while (!outBuffer.isEmpty()) {
							 NetworkMessage message = outBuffer.poll();
							 if ( message == null ) continue;
							 oos.writeObject(message);
							 System.out.println("Message sent!");
						 }
						 oos.flush();
						 try {
							outBuffer.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					 }
				 }
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
	 }
	 
	 private class Receiver implements Runnable {
		private ObjectInputStream in;
		private final Socket server;
		private final Queue<NetworkMessage> inBuffer;
		
		public Receiver(Socket server, Queue<NetworkMessage> inBuffer) {
			this.server	= server;
			this.inBuffer	= inBuffer;
		}
		
		public void run() {
			try {
				this.in = new ObjectInputStream(new BufferedInputStream(server.getInputStream()));
				while (true) {
					synchronized (inBuffer) {
						NetworkMessage message = (NetworkMessage) in.readObject();
						if (inBuffer.offer(message)) {
							inBuffer.notifyAll();
						}
						System.out.println(((ExampleNetworkMessage) message.getMessage()).getData());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	 }
}
