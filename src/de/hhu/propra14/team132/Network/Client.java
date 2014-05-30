package de.hhu.propra14.team132.Network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.hhu.propra14.team132.gameSystem.Communicable;
import de.hhu.propra14.team132.gameSystem.GameManager;
import de.hhu.propra14.team132.gameSystem.Message;
import de.hhu.propra14.team132.gameSystem.MessageType;

/**
 * This class is used to communicate with the {@link Sever} class.
 * 
 * @author Sebastian Sura - link Sebastian.Sura@uni-duesseldorf.de
 *
 * @see Server
 */
public class Client implements Communicable {
	/*
	 * This class does the following:
	 * 1) Create a 'FutureTask' with a parameter of a 'ClientHandler' instance
	 * 2) Give this 'FutureTask' to another thread. In this thread call the 'call' function of 'ClientHandler'
	 * 3) In that thread the whole communication takes place.
	 */
	 
	 public static void main2(String[] args) {	
		try {
			Client client = new Client(null, args[0], 3141);
			final Queue<String> list = new LinkedList<String>();
			Client.checkForHosts(args[0].substring(0, args[0].lastIndexOf('.')), list);
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						synchronized(list) {
							try {
								list.wait();
								String s = list.poll();
								if (s.equals("")) break;
								System.out.println(s);
							} catch (InterruptedException e) {
								break;
							}
						}
					}
				}
				
			});
			thread.start();
			
			String in = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			do {
				in = reader.readLine();
				if (in == "exit") {
					break;
				}
				client.receiveMessage(new ExampleNetworkMessage(in, client));
			} while (!in.equals("exit"));
			client.close();
		} catch (Exception e) {
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
	 private final GameManager manager;
	 
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
		this.server 				= new Socket(serverIP, port);
		this.sendThread 			= new Thread(new Sender(server, messageOutBuffer));
		this.receiveThread 			= new Thread(new Receiver(server, messageInBuffer));
		this.manager				= g;
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
		 this.sendCloseMessage();
		 
		 if (sendThread != null && this.sendThread.isAlive()) {
			 sendThread.interrupt();
		 }
		 
		 if (receiveThread != null && this.receiveThread.isAlive()) {
			 receiveThread.interrupt();
		 }
		 
		 if (server != null) {
			 server.close();
		 }
	 }
	 
	 /**
	  * Searches for hosts in the subnet and stores found hosts into list.
	  * On inserting elements into list, it notifies all listener and after
	  * searching through the whole subnet it offers the empty String ("")
	  * and notifies all the listener for the last time.
	  * 
	  * @param subnet - {@link String} The Subnet it searches through
	  * @param queue  - {@link Queue} The Queue, where the found hosts are stored
	  * 
	  * @see Queue
	  */
	 public static void checkForHosts(final String subnet, final Queue<String> queue) {
		 Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 256; ++i) {
					String connect = subnet + "." + i;
					System.out.println("Trying to connect to: " + connect);
					Socket possibleServer = new Socket();
					try {
						possibleServer.connect(new InetSocketAddress(connect, 3141), 200);
						if (possibleServer.isConnected()) {
							// tell the server we do not want to connect
							BufferedOutputStream out = new BufferedOutputStream(possibleServer.getOutputStream());
							out.write(1);
							out.flush();
							out.close();
							synchronized(queue) {
								queue.offer(connect);
								queue.notifyAll();
							}
							possibleServer.close();
						}
					} catch (IOException e) {
						System.out.println("Could not connect to: " + connect);
					}
				}
				// tell the listener that the last adress is checked
				synchronized(queue) {
					queue.add(new String(""));
					queue.notifyAll();
				}
			}
			 
		 });
		 thread.start();
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
	 
	 private void sendCloseMessage() {
		 synchronized (this.messageOutBuffer) {
			 if (this.messageOutBuffer.offer(NetworkMessage.CLOSE_MESSAGE)) {
				 this.messageOutBuffer.notifyAll();
			 }
		 }
		 synchronized (this.messageInBuffer) {
			 NetworkMessage mes;
			 try {
				this.messageInBuffer.wait();
				
				 while ((mes = this.messageInBuffer.poll()) != NetworkMessage.CLOSE_MESSAGE) {
					 // discard them and wait for the next
					 this.messageInBuffer.wait();
				 }
			 } catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
	 }
	 
	public void register() {
		this.manager.register(this, MessageType.values());
	}
	 
	 private int getClientID() throws IOException {
		 // tell the server we want to connect
		 BufferedOutputStream out = new BufferedOutputStream(this.server.getOutputStream());
		 out.write(0);
		 out.flush();
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
				mainloop: while (true) {
					 synchronized(this.outBuffer) {
						 while (!this.outBuffer.isEmpty()) {
							 NetworkMessage message = this.outBuffer.poll();
							 if ( message == null ) continue;
							 oos.writeObject(message);
							 if (message == NetworkMessage.CLOSE_MESSAGE) {
								 break mainloop;
							 }
							 System.out.println("Message sent!");
						 }
						 oos.flush();
						 try {
							 this.outBuffer.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					 }
				 }
				oos.close();
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
			this.server		= server;
			this.inBuffer	= inBuffer;
		}
		
		public void run() {
			try {
				this.in = new ObjectInputStream(new BufferedInputStream(server.getInputStream()));
				while (true) {
					synchronized (this.inBuffer) {
						NetworkMessage message = (NetworkMessage) in.readObject();
						message.getMessage().setSender(Client.this);
						if (this.inBuffer.offer(message)) {
							this.inBuffer.notifyAll();
							Client.this.manager.sendMessage(message.getMessage());
						}
						if (message == NetworkMessage.CLOSE_MESSAGE) {
							break;
						}
					}
				}
				this.in.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	 }
}
