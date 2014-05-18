package de.hhu.propra14.team132.Network;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.net.*;

import de.hhu.propra14.team132.gameSystem.GameManager;

/**
 *
 * @author <a email-to="Sebastian.Sura@uni-duesseldorf.de">Sebastian Sura</a>
 *
 */
public class Server {
	/* static variables */
	//in this logger the Server will log the events (connects, disconnects, etc.)
	private static final Logger	log		= Logger.getLogger(Server.class.getName());
	
	/* attributes */
	//in this Threadpool every Thread that the server uses lives
	private final ExecutorService	connectionPool;
	//this is the socket of the server
	private final ServerSocket	serverSocket;
	//the server wil listen to this port
	private final int		port;
	//The network service will handle all I/O
	private final NetworkService	service;
	
	public static void main(String[] argv) {
		try {
			new Server(null, 3141);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a Server that forwards {@link NetworkMessage}s between {@link Client}s through a port.
	 * 
	 * @param g 		The {@link GameManager} that started the game.
	 * @param port 		The port that the server should listen to.
	 * @throws IOException 	Throws an IOException if the ServerSocket could not be created.
	 * @see Client
	 */
	public Server(GameManager g, int port) throws IOException {
		this.port		= port;
		this.connectionPool	= Executors.newCachedThreadPool();
		this.serverSocket	= new ServerSocket(port);
		FileHandler fileHandler	= new FileHandler("log.txt");
		fileHandler.setLevel(Level.INFO);
		Server.log.addHandler(fileHandler);
		
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
								Server.log.warning("Closing the ServerSocket forcefully!");
								Server.this.serverSocket.close();
							}
						} catch (IOException | InterruptedException e) {
							e.printStackTrace();
						} 
					}
			});
	}
	
	/**
	 * Return the port the server listens to.
	 * 
	 * @return Returns the port the server listens to
	 */
	public int getPort() {
		return this.port;
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
					  this.handlerList.add(newHandler);
					  this.pool.execute(newHandler);
				 }
			 } catch (IOException e) {
				 // do not print the stacktrace, because this was not an error
				 Server.log.warning("NetworkService got interrupted");
			 } finally {
				Server.log.info("NetworkService is getting stopped");
				
				// do not accept new requests
				this.pool.shutdown();
				try {
					//wait up to 4 seconds for the end of pending requests
					this.pool.awaitTermination(4L, TimeUnit.SECONDS);
					if (!serverSocket.isClosed()) {
						Server.log.warning("NetworkService stopped manually");
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
			if (mes.getID() != this.assoziatedID) {
				synchronized (this.outMessages) {
					if (this.outMessages.offer(mes)) {
						this.outMessages.notifyAll();
					}
					
				}
			}
		}
		
		public void run() {
			
			try {
				sentIDToClient();
			} catch (IOException e) {
				// this should never happen
				e.printStackTrace();
				//TODO: close client
				return;
			}
			
			this.pool.execute(new InputHandler(client, inMessages));
			this.pool.execute(new OutputHandler(client, outMessages));
			
			while (true) {
				synchronized(this.inMessages) {
					try {
						this.inMessages.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					while (!inMessages.isEmpty()) {
						this.manager.sendMessage(inMessages.poll());
					}
				}
			}
			
			
		}
		
		private void sentIDToClient() throws IOException {
			// sent the id to the client, so he knows who he is
			ObjectOutputStream out = new ObjectOutputStream(this.client.getOutputStream());
			out.writeInt(this.assoziatedID);
			out.flush();
			Server.log.info("Connecting to Client " + this.assoziatedID);
			
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
					this.in = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
					while (true) {
						// read a network message from the input stream
						NetworkMessage message = (NetworkMessage) in.readObject();
						if (message.getID() == NetworkMessage.requestingConnectionStopID) {
							break;
						}
						synchronized (this.inMessages) {
							this.inMessages.offer(message);
							this.inMessages.notifyAll();
						}
						Server.log.finest("Client #" + Handler.this.assoziatedID + "\nReceived message");
					}
				} catch (IOException e) {
					Server.log.severe("Client #" + Handler.this.assoziatedID + "\nConnection cut without sending close message.\n" + e.getMessage());
				} catch (ClassNotFoundException e) {
					Server.log.severe("Client #" + Handler.this.assoziatedID + "\nNot recognized Object was sent.");
					//e.printStackTrace();
				} finally {
					if (!this.client.isClosed()) {
						Server.log.info("Client #" + Handler.this.assoziatedID + "\nClosing the client");
						try {
							this.in.close();
							this.client.close();
						} catch (IOException e) {
							Server.log.severe("Client #" + Handler.this.assoziatedID + "\nCould not close client.\n" + e.getMessage());
							//e.printStackTrace();
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
					this.out = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));
					while (true) {
						//wait for messages to come
						//while (outMessages.isEmpty());
						//if we got one, send all
						synchronized (this.outMessages) {
							if (!outMessages.isEmpty()) {
								NetworkMessage message = outMessages.poll();
								this.out.writeObject(message);
								Server.log.finest("Client #" + Handler.this.assoziatedID + "\nSent a message!.");
							}
							this.out.flush();
							try {
								this.outMessages.wait();
							} catch (InterruptedException e) {
								Server.log.severe("Client #" + Handler.this.assoziatedID + "\nWhile waiting for new messages, I got Interrupted\n" + e.getMessage());
								//e.printStackTrace();
							}
						}
					}
					
				} catch (IOException e) {
					Server.log.warning("Client #" + Handler.this.assoziatedID + "\nConnection cut without sending close message.\n" + e.getMessage());
					//e.printStackTrace();
				} finally {
					if (!this.client.isClosed()) {
						Server.log.info("Client #" + Handler.this.assoziatedID + "\nClosing the client.");
						try {
							this.out.close();
							this.client.close();
						} catch (IOException e) {
							Server.log.severe("Client #" + Handler.this.assoziatedID + "\nCould not close client.\n" + e.getMessage());
							//e.printStackTrace();
						}
					}
				}
			}
		}
	}
}