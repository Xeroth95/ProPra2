package de.hhu.propra14.team132.Network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.net.*;

public class Server {
	
	private final ExecutorService connectionPool;
	private final ServerSocket serverSocket;
	private int port;
	
	public static void main(String[] args) {
		try {
			Server server = new Server(3141);
		} catch(IOException e) {
			
		}
	}
	
	public Server(int port) throws IOException {
		this.port			= port;
		this.connectionPool	= Executors.newCachedThreadPool();
		this.serverSocket	= new ServerSocket(port);
		
		/*
		 * Start a new Thread. This thread will wait for clients to connect
		 * and create new Threads for them to communicate
		 */
		Thread clientHandler = new Thread(new NetworkService(connectionPool, serverSocket));
		clientHandler.start();
		
		/*
		 * Add a shutdown hook, so we can kill the connections
		 * and their thread before we quit the application
		 */
		 
		 Runtime.getRuntime().addShutdownHook(
			new Thread() {
					public void run() {
						Server.this.connectionPool.shutdown();
						try {
							//wait up to 4 seconds
							Server.this.connectionPool.awaitTermination(4L, TimeUnit.SECONDS);
							if (!Server.this.serverSocket.isClosed()) {
								System.out.println("Closing the ServerSocket forcefully!");
								Server.this.serverSocket.close();
							}
						} catch (IOException | InterruptedException e) {
							e.printStackTrace();
						} 
					}
			});
	}
	
	// This runnable awaits connections and forwards them
	private class NetworkService implements Runnable {
		private final ServerSocket serverSocket;
		private final ExecutorService pool;
		
		public NetworkService(ExecutorService pool, ServerSocket socket) {
			this.pool			= pool;
			this.serverSocket	= socket;
		}
		
		// this will get executed in the Thread
		public void run() {
			try {
				/*
				 * loops forever:
				 * 1) wait for a client
				 * 2) create a new Handler Thread for it
				 * 3) goto 1)
				 * 
				 * this loop will stop when it is told to through a 'stop' command
				 * which will throw an IOException which is catched here and ends the loop
				 * the cleanup is done in the finally clause
				 * 
				 */
				 
				 while (true) {
					 /*
					  * wait for a client with 'accept' (this will block the thread)
					  * then get a thread from the Threadpool (ExecutorService pool)
					  * which will run the 'run' method of the 'Handler'-thread.
					  * The Handerthread needs:
					  * 1) the ServerSocket
					  * 2) the Client socket
					  */
					  
					  //wait for a client
					  Socket clientSocket = serverSocket.accept();
					  
					  //start a new Handerthread
					  pool.execute(new Handler(serverSocket, clientSocket));
				 }
			 } catch (IOException e) {
				 // do not print the stacktrace, because this was not an error
				 System.out.println("--- Interrupt NetworkService-run");
			 } finally {
				System.out.println("--- Stop NetworkService");
				
				// do not accept new requests
				pool.shutdown();
				try {
					//wait up to 4 seconds for the end of pending requests
					pool.awaitTermination(4L, TimeUnit.SECONDS);
					if (!serverSocket.isClosed()) {
						System.out.println("--- Stopped NetworkService");
						serverSocket.close();
					}
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				} 
			 }
		}
	}
	
	// This runnable handles the client requests
	private class Handler implements Runnable {
		private final Socket client;
		private final ServerSocket server;
		
		public Handler(ServerSocket server, Socket client) {
			this.client = client;
			this.server = server;
		}
		
		public void run() {
			PrintWriter out = null;
			InputStreamReader in = null;
			
			try {
				System.out.println("starting service " + Thread.currentThread());
				out = new PrintWriter(client.getOutputStream(), true);
				in = new InputStreamReader(client.getInputStream());
				char[] messageLengthBuffer = new char[4];
				//first the message length is send
				int read = in.read(messageLengthBuffer, 0, 4);
				int messageLength = getMessageLength(messageLengthBuffer);
				System.out.println("Erhalten : " + messageLength + "([ " 	+ (int)messageLengthBuffer[0] + ","
																			+ (int)messageLengthBuffer[1] + ","
																			+ (int)messageLengthBuffer[2] + ","
																			+ (int)messageLengthBuffer[3]
																			+  " ], read = " + read + ")");
				if (messageLength <= 0) return;															
				char[] message = new char[messageLength];
				in.read(message, 0, messageLength);
				System.out.println(new String(message));
			} catch (IOException e) {
				System.out.println("IOException --- in Handlerthread " + Thread.currentThread());
				e.printStackTrace();
			} finally {
				if (!client.isClosed()) {
					System.out.println("--- Closed client");
					try {
						client.close();
					} catch (IOException e) {
						System.out.println("Could not close client.");
						e.printStackTrace();
					}
				}
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
