package de.hhu.propra14.team132.network;

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
import de.hhu.propra14.team132.gameSystem.Message;

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
	
	public void sendMessage(NetworkMessage mes) {
		this.service.sendMessage(mes);
	}
	
	// This runnable awaits connections and forwards them
	private class NetworkService implements Runnable {
		private final ServerSocket serverSocket;
		private final ExecutorService pool;
		private final List<Handler> handlerList;
		
		private int curID;
		
		public NetworkService(ExecutorService pool, ServerSocket socket) {
			this.pool		= pool;
			this.serverSocket	= socket;
			this.handlerList	= new ArrayList<Handler>();
			this.curID		= 0;
		}
		
		public void sendMessage(NetworkMessage mes) {
			for (Handler h : handlerList) {
				h.sendMessage(mes);
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
					  * The Handlerthread needs:
					  * 1) the ServerSocket
					  * 2) the Client socket
					  */
					  
					  //wait for a client
					  Socket clientSocket = serverSocket.accept();
					  
					  //start a new Handlerthread
					  Handler newHandler = new Handler(this, clientSocket, pool, getNextID());
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
		
		private int getNextID() {
			return curID++;
		}
	}
	
	// This runnable handles the client requests
	private class Handler implements Runnable {
		private final Socket client;
		private final ExecutorService pool;
		private final Queue<NetworkMessage> inMessages;
		private final Queue<NetworkMessage> outMessages;
		private final NetworkService manager;
		private final int assoziatedID;
		
		public Handler(NetworkService manager, Socket client, ExecutorService pool, int assoziatedID) {
			this.client		= client;
			this.pool		= pool;
			this.inMessages 	= new ConcurrentLinkedQueue<NetworkMessage>();
			this.outMessages	= new ConcurrentLinkedQueue<NetworkMessage>();
			this.manager		= manager;
			this.assoziatedID	= assoziatedID;
		}
		
		public void sendMessage(NetworkMessage mes) {
			// do not sent the messages sent by client to the client
			if (mes.getID() != this.assoziatedID)
				this.outMessages.offer(mes);
		}
		
		public void run() {
			
			sentIDToClient();
			
			this.pool.execute(new InputHandler(client, inMessages));
			this.pool.execute(new OutputHandler(client, outMessages));
			
			while (true) {
				while (!inMessages.isEmpty()) {
					this.manager.sendMessage(inMessages.poll());
				}
			}
			
			
		}
		
		private void sentIDToClient() {
			// sent the id to the client, so he knows who he is
		}
		
		private class InputHandler implements Runnable {
			private ObjectInputStream in;
			private final Queue<NetworkMessage> inMessages;
			private final Socket client;
			
			public InputHandler(Socket client, Queue<NetworkMessage> inMessages) {
				this.client	= client;
				this.inMessages = inMessages;
			}
			
			public void run() {
				try {
					this.in = new ObjectInputStream(client.getInputStream());
					while (true) {
						// read a network message from the input stream
						NetworkMessage message = (NetworkMessage) in.readObject();
						this.inMessages.offer(message);
						System.out.println("Got that message!");
					}
				} catch (IOException e) {
					System.out.println("IOException --- in Handlerthread " + Thread.currentThread());
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
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
		}
		
		private class OutputHandler implements Runnable {
			
			private ObjectOutputStream		out;
			private final Queue<NetworkMessage>	outMessages;
			private final Socket			client;
			
			public OutputHandler(Socket client, Queue<NetworkMessage> outMessages) {
				this.client		= client;
				this.outMessages	= outMessages;
			}
			
			public void run() {
				try {
					this.out = new ObjectOutputStream(client.getOutputStream());
					while (true) {
						//wait for messages to come
						while (outMessages.isEmpty());
						//if we got one, send all
						while (!outMessages.isEmpty()) {
							NetworkMessage message = outMessages.poll();
							this.out.writeObject(message);
							System.out.println("Sent that message!");
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
	}
}
