package de.hhu.propra14.team132.network;

import java.io.*;
import java.net.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Arrays;

public class Client {
	/*
	 * This class does the following:
	 * 1) Create a 'FutureTask' with a parameter of a 'ClientHandler' instance
	 * 2) Give this 'FutureTask' to another thread. In this thread call the 'call' function of 'ClientHandler'
	 * 3) In that thread the whole communication takes place.
	 */
	 
	 public static void main(String[] args) {
		 if (args.length < 2) {
			System.out.println("No message to send!");
			System.exit(1);
		}
		
		try {
			Client client = new Client(args[0], 3141);
			client.send(args[1]);
			Thread.sleep(6000);
			client.close();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	 }
	 
	 
	 private final Thread sendThread;
	 private final Thread receiveThread;
	 private final Queue<String> messageInBuffer;
	 private final Queue<String> messageOutBuffer;
	 private volatile boolean toSend;
	 private volatile boolean hasReceived;
	 private Socket server;
	 
	 
	 public Client(String serverIP, int port) throws IOException {
		this.toSend						= false;
		this.hasReceived				= false;
		this.messageInBuffer	= new ConcurrentLinkedQueue<String>();
		this.messageOutBuffer	= new ConcurrentLinkedQueue<String>();
		this.server = new Socket(serverIP, port);
		this.sendThread = new Thread(new Sender(server));
		this.sendThread.start();
		this.receiveThread = new Thread(new Receiver(server));
		this.receiveThread.start();
	 }
	 
	 public void send(String toSend) throws IOException {
		 this.toSend = this.messageOutBuffer.offer(toSend);
	 }
	 
	 // this method should never be called
	 // its only here for test purposes
	 public String receive() throws IOException {
		 if (this.hasReceived) {
			 String ret = this.messageInBuffer.poll();
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
	 
	 private class Sender implements Runnable {
		 Socket server;
		 
		 public Sender(Socket server) {
			 this.server = server;
		 }
		 
		 public void run() {
			 while (true) {
				 if (Client.this.toSend) {
					 try {
						 String message = Client.this.messageOutBuffer.poll();
						 if ( message == null ) continue;
						 Client.this.toSend = (Client.this.messageOutBuffer.peek() == null);
						 char[] messageLengthBuffer = toMessageLengthBuffer(message.length());
						 OutputStreamWriter out = new OutputStreamWriter(server.getOutputStream());
						 System.out.println("Sent : [" 	+ (int)messageLengthBuffer[0] + ", "
														+ (int)messageLengthBuffer[1] + ", "
														+ (int)messageLengthBuffer[2] + ", "
														+ (int)messageLengthBuffer[3] + "]\nShould be : " + message.length());
						 out.write(messageLengthBuffer, 0, 4);
						 out.flush();
						 out.write(message, 0, message.length());
						 out.flush();
						 System.out.println("Message sent!");
					 } catch (IOException e) {
						 e.printStackTrace();
					 }
				 }
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
		 public Receiver(Socket server) {
			 
		 }
		 
		 public void run() {
			 
		 }
	 }
}
