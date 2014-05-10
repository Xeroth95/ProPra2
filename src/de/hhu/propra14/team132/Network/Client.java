package de.hhu.propra14.team132.Network;

import java.io.*;
import java.net.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Arrays;

import de.hhu.propra14.team132.gameSystem.Message;

public class Client {
	/*
	 * This class does the following:
	 * 1) Create a 'FutureTask' with a parameter of a 'ClientHandler' instance
	 * 2) Give this 'FutureTask' to another thread. In this thread call the 'call' function of 'ClientHandler'
	 * 3) In that thread the whole communication takes place.
	 */
	 
	 public static void main(String[] args) {	
		try {
			Client client = new Client(args[0], 3141);
			String in = null;
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			do {
				in = reader.readLine();
				client.sendMessage(new ExampleNetworkMessage(in));
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
	 private volatile boolean toSend;
	 private volatile boolean hasReceived;
	 private Socket server;
	 private int clientID;
	 
	 
	 public Client(String serverIP, int port) throws IOException {
		this.toSend			= false;
		this.hasReceived		= false;
		this.messageInBuffer		= new ConcurrentLinkedQueue<NetworkMessage>();
		this.messageOutBuffer		= new ConcurrentLinkedQueue<NetworkMessage>();
		this.server 			= new Socket(serverIP, port);
		this.sendThread 		= new Thread(new Sender(server));
		this.receiveThread 		= new Thread(new Receiver(server));
		this.sendThread.start();
		this.receiveThread.start();
		this.clientID			= -1;
	 } 
	 
	 // this method should never be called
	 // its only here for test purposes
	 public NetworkMessage receive() throws IOException {
		 if (this.hasReceived) {
			 NetworkMessage ret = this.messageInBuffer.poll();
			 hasReceived = (this.messageInBuffer.peek() != null);
			 return ret;
		 } else {
			 return null;
		 }
	 }
	 
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
	 
	 public void sendMessage(Message message) {
		 NetworkMessage netMessage = new NetworkMessage(message, this.clientID);
		 this.toSend = this.messageOutBuffer.offer(netMessage);
	 }
	 
	 private class Sender implements Runnable {
		 Socket server;
		 
		 public Sender(Socket server) {
			 this.server = server;
		 }
		 
		 public void run() {
			 try {
				ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
				while (true) {
					 if (Client.this.toSend) {
						 NetworkMessage message = Client.this.messageOutBuffer.poll();
						 if ( message == null ) continue;
						 Client.this.toSend = (Client.this.messageOutBuffer.peek() == null);
						 oos.writeObject(message);
						 System.out.println("Message sent!");
					 }
				 }
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
		 
		 public char[] toMessageLengthBuffer(int length) {
			 // int = 4bytes
			 char[] ret = new char[4];
			 ret[0] = (char) (length >> 0);
			 ret[1] = (char) (length >> 8);
			 ret[2] = (char) (length >> 16);
			 ret[3] = (char) (length >> 24);
			 
			 return ret;
		 }
	 }
	 
	 private class Receiver implements Runnable {
		private ObjectInputStream in;
		private final Socket server;
		
		public Receiver(Socket server) {
			this.server = server;
		}
		
		public void run() {
			try {
				this.in = new ObjectInputStream(server.getInputStream());
				while (true) {
					NetworkMessage message = (NetworkMessage) in.readObject();
					if (Client.this.messageInBuffer.offer(message)) {
						Client.this.hasReceived = true;
					}
					System.out.println(((ExampleNetworkMessage) message.getMessage()).getData());
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		private int getMessageLength(char[] buffer) {
			int length = 0;
			for (int i = 0; i < buffer.length; ++i) {
				length += ((int) buffer[i]) << 8*i;
			}
			
			return length;
		}
	 }
}
