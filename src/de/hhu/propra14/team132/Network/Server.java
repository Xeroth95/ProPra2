package de.hhu.propra14.team132.Network;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.net.*;

import de.hhu.propra14.team132.gameSystem.GameManager;

public class Server {
	
	private final ExecutorService	connectionPool;
	private final ServerSocket	serverSocket;
	private final int		port;
	private final NetworkService	service;
	
	public static void main(String[] argv) {
		try {
			Server server = new Server(null, 3141);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Server(GameManager g, int port) throws IOException {
		this.port		= port;
		this.connectionPool	= Executors.newCachedThreadPool();
		this.serverSocket	= new ServerSocket(port);
		
		/*
		 * Start a new Thread. This thread will wait for clients to connect
		 * and create new Threads for them to communicate
		 */
		
		this.service = new NetworkService(connectionPool, serverSocket);
		Thread clientHandler = new Thread(this.service);
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
	
	public void sendMessage(String str) {
		this.service.sendMessage(str);
	}
	
	// This runnable awaits connections and forwards them
	private class NetworkService implements Runnable {
		private final ServerSocket serverSocket;
		private final ExecutorService pool;
		private final List<Handler> handlerList;
		public NetworkService(ExecutorService pool, ServerSocket socket) {
			this.pool		= pool;
			this.serverSocket	= socket;
			this.handlerList	= new ArrayList<Handler>();
		}
		
		public void sendMessage(String str) {
			for (Handler h : handlerList) {
				h.sendMessage(str);
			}
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
					  Handler newHandler = new Handler(serverSocket, clientSocket, pool);
					  handlerList.add(newHandler);
					  pool.execute(newHandler);
				 }
			 } catch (IOException e) {
				 // do not print the stacktrace, because this was not an error
				 System.out.println("--- Interrupt NetworkService-run");
			 } finally {
				System.out.println("--- Stop NetworkService");
				
				// do not accept new requests
				this.pool.shutdown();
				try {
					//wait up to 4 seconds for the end of pending requests
					this.pool.awaitTermination(4L, TimeUnit.SECONDS);
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
		private final ExecutorService pool;
		private final Queue<String> inMessages;
		private final Queue<String> outMessages;
		
		public Handler(ServerSocket server, Socket client, ExecutorService pool) {
			this.client		= client;
			this.server		= server;
			this.pool		= pool;
			this.inMessages 	= new ConcurrentLinkedQueue<String>();
			this.outMessages	= new ConcurrentLinkedQueue<String>();
		}
		
		public void sendMessage(String str) {
			this.outMessages.offer(str);
		}
		
		public void run() {
			/*PrintWriter out = null;
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
			}*/
			
			this.pool.submit(new InputHandler(client, server, inMessages));
			this.pool.submit(new OutputHandler(client, server, outMessages));
			
			while (true) {
				while (!inMessages.isEmpty()) {
					outMessages.offer(inMessages.poll());
				}
			}
			
			
		}
		
		private class InputHandler implements Runnable {
			private InputStreamReader in;
			private Queue<String> inMessages;
			private Socket client;
			private ServerSocket server;
			
			public InputHandler(Socket client, ServerSocket server, Queue<String> inMessages) {
				this.client	= client;
				this.server 	= server;
				this.inMessages = inMessages;
			}
			
			public void run() {
				try {
					this.in = new InputStreamReader(client.getInputStream());
					while (true) {
						System.out.println("starting service " + Thread.currentThread());
						char[] messageLengthBuffer = new char[4];
						//first the message length is send
						int read = in.read(messageLengthBuffer, 0, 4);
						int messageLength = getMessageLength(messageLengthBuffer);
						System.out.println("Erhalten : " 	+ messageLength + "([ " 	
											+ (int)messageLengthBuffer[0] + ","
											+ (int)messageLengthBuffer[1] + ","
											+ (int)messageLengthBuffer[2] + ","
											+ (int)messageLengthBuffer[3]
											+  " ], read = " + read + ")");
						if (messageLength <= 0) return;															
						char[] message = new char[messageLength];
						this.in.read(message, 0, messageLength);
						String messageString = new String(message);
						
						this.inMessages.offer(messageString);					
						System.out.println(new String(message));
					}
				} catch (IOException e) {
					System.out.println("IOException --- in Handlerthread " + Thread.currentThread());
					e.printStackTrace();
				} finally {
					if (!this.client.isClosed()) {
						System.out.println("--- Closed client");
						try {
							this.in.close();
							this.client.close();
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
		
		private class OutputHandler implements Runnable {
			
			private OutputStreamWriter	out;
			private Queue<String>		outMessages;
			private Socket			client;
			private ServerSocket		server;
			
			public OutputHandler(Socket client, ServerSocket server, Queue<String> outMessages) {
				this.client		= client;
				this.server 		= server;
				this.outMessages	= outMessages;
			}
			
			public void run() {
				try {
					this.out = new OutputStreamWriter(client.getOutputStream());
					while (true) {
						//wait for messages to come
						while (outMessages.isEmpty());
						//if we got one, send all
						while (!outMessages.isEmpty()) {
							String toSend = outMessages.poll();
							int messageLength = toSend.length();
							char[] messageLengthBuffer = getMessageLengthBuffer(messageLength);
							
							this.out.write(messageLengthBuffer, 0, 4);
							this.out.flush();
							this.out.write(toSend.toCharArray(), 0, messageLength);
							this.out.flush();
							
							System.out.println("I wrote : " + toSend);
						}
					}
					
				} catch (IOException e) {
					System.out.println("IOException --- in Handlerthread " + Thread.currentThread());
					e.printStackTrace();
				} finally {
					if (!this.client.isClosed()) {
						System.out.println("--- Closed client");
						try {
							this.out.close();
							this.client.close();
						} catch (IOException e) {
							System.out.println("Could not close client.");
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		private char[] getMessageLengthBuffer(int length) {
			char[] buffer = new char[4];
			for (int i = 0; i < buffer.length; ++i) {
				buffer[i] = (char) (length >> 8 * i);
			}
			
			return buffer;
		}
	}
}
