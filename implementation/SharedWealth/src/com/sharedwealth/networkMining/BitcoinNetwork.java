package com.sharedwealth.networkMining;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.sharedwealth.demo.User;
import com.sharedwealth.demo.User2;
import com.sharedwealth.mining.Miner;
import com.sharedwealth.mining.ProofOfWork;
import com.sharedwealth.transaction.Transaction;

/*
 * The server that can be run both as a console application or a GUI
 */
public class BitcoinNetwork {
	// a unique ID for each connection
	private static int uniqueId;
	// an ArrayList to keep the list of the Client
	private ArrayList<ClientThread> al;
	private ArrayList<User2> users;
	private ArrayList<Miner> miner;
	
	private SimpleDateFormat sdf;
	
	private int port;
	// the boolean that will be turned of to stop the server
	private boolean keepGoing;
	

	
	public BitcoinNetwork(int port) {
	
		this.port = port;
		// to display hh:mm:ss
		sdf = new SimpleDateFormat("HH:mm:ss");
		// ArrayList for the Client list
		al = new ArrayList<ClientThread>();
		users=new ArrayList<User2>();
		miner=new ArrayList<Miner>();
		}
	
	public void start() {
		keepGoing = true;
		/* create socket server and wait for connection requests */
		try 
		{
			// the socket used by the server
			ServerSocket serverSocket = new ServerSocket(port);

			// infinite loop to wait for connections
			while(keepGoing) 
			{
				// format message saying we are waiting
				display("Bitcoin Network started...");
				
				Socket socket = serverSocket.accept();  	// accept connection
				// if I was asked to stop
				if(!keepGoing)
					break;
				ClientThread t = new ClientThread(socket);  // make a thread of it
				al.add(t);
//				System.out.println("Here in start");
//				transactions.add(t);// save it in the ArrayList
				t.start();
			}
			// I was asked to stop
			try {
				serverSocket.close();
				for(int i = 0; i < al.size(); ++i) {
					ClientThread tc = al.get(i);
					try {
					tc.sInput.close();
					tc.sOutput.close();
					tc.socket.close();
					}
					catch(IOException ioE) {
						// not much I can do
					}
				}
			}
			catch(Exception e) {
				display("Exception closing the server and clients: " + e);
			}
		}
		// something went bad
		catch (IOException e) {
            String msg = sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
			display(msg);
		}
	}		
   

	/*
	 * Display an event (not a message) to the console or the GUI
	 */
	private void display(String msg) {
		String time = sdf.format(new Date()) + " " + msg;
//		if(sg == null)
			System.out.println(time);
//		else
//			sg.appendEvent(time + "\n");
	}
	/*
	 *  to broadcast a message to all Clients
	 */
	private synchronized void broadcast(String message) {
		// add HH:mm:ss and \n to the message
		String time = sdf.format(new Date());
		String messageLf = time + " " + message + "\n";
		// display message on console or GUI
//		if(sg == null)
			System.out.print("This is a test:"+messageLf);
//		else
//			sg.appendRoom(messageLf);     // append in the room window
//		
		// we loop in reverse order in case we would have to remove a Client
		// because it has disconnected
		for(int i = al.size(); --i >= 0;) {
			ClientThread ct = al.get(i);
			// try to write to the Client if it fails remove it from the list
			if(!ct.writeMsg(messageLf)) {
				al.remove(i);
				display("Disconnected Client  removed from list.");
			}
		}
	}

	// for a client who logoff using the LOGOUT message
	synchronized void remove(int id) {
		// scan the array list until we found the Id
		for(int i = 0; i < al.size(); ++i) {
			ClientThread ct = al.get(i);
			// found it
			if(ct.id == id) {
				al.remove(i);
				return;
			}
		}
	}
	
	/*
	 *  To run as a console application just open a console window and: 
	 * > java Server
	 * > java Server portNumber
	 * If the port number is not specified 1500 is used
	 */ 
	public static void main(String[] args) {
		// start server on port 1500 unless a PortNumber is specified 
		int portNumber = 1500;
		
		// create a server object and start it
		ProofOfWork.difficulty=2;
		System.out.println("POW difficulty:"+ProofOfWork.difficulty);
		BitcoinNetwork server = new BitcoinNetwork(portNumber);
		
		server.start();
	}

	/** One instance of this thread will run for each client */
	class ClientThread extends Thread {
		// the socket where to listen/talk
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		// my unique id (easier for deconnection)
		int id;
		Miner m;
		String date;
		User2 newUser;

		// Constructore
		ClientThread(Socket socket) {
			// a unique id
			id = ++uniqueId;
			this.socket = socket;
			/* Creating both Data Stream */
			System.out.println("Thread trying to create Object Input/Output Streams");
			try
			{
				// create output first
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput  = new ObjectInputStream(socket.getInputStream());
			
				
			}
			catch (IOException e) {
				display("Exception creating new Input/output Streams: " + e);
				return;
			}
			// have to catch ClassNotFoundException

			
            date = new Date().toString() + "\n";
		}

		// what will run forever
		public void run() {
			// to loop until LOGOUT
			boolean keepGoing = true;
			while(keepGoing) {
				// read a String (which is an object)
				try {
					Object o=sInput.readObject();
					if(o instanceof User2){
					newUser=(User2)o;
						users.add(newUser);
						for(User2 u:users){
							if(u.hasReceiverKey()&& newUser.getAddress().equals(u.getAddress())){
								
								sOutput.writeObject("Received following transaction:\n"+u.toString());
								
							
							}
							
							else{
								if(newUser.getAddress().equals(u.getAddress())){
								sOutput.writeObject("Received following transaction:\n"+u.toString());
								
//								u.setAmount(24);
//								sOutput.writeObject("Updated amount\n"+u.getAmount()+","+u.getReceiverPk());
								}
							}
							}
					}
					else if(o instanceof Miner){
						m=(Miner)o;
						for(User2 u:users){
							if(u.hasReceiverKey()){
								sOutput.writeObject(u.getTransaction());
								
							}
						}
					}
				}
				
				// Switch on the type of message receive
						catch(IOException e) {
							display("Error sending message to ");
							display(e.toString());
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

			}
			// remove myself from the arrayList containing the list of the
			// connected Clients
			remove(id);
			close();
		}
		
		// try to close everything
		private void close() {
			// try to close the connection
			try {
				if(sOutput != null) sOutput.close();
			}
			catch(Exception e) {}
			try {
				if(sInput != null) sInput.close();
			}
			catch(Exception e) {};
			try {
				if(socket != null) socket.close();
			}
			catch (Exception e) {}
		}

		/*
		 * Write a String to the Client output stream
		 */
		private boolean writeMsg(String msg) {
			// if Client is still connected send the message to it
			if(!socket.isConnected()) {
				close();
				return false;
			}/*
//			 write the message to the stream
			try {
				if(newUser.getReceiverPk()!=null){
				
				String recvKey=newUser.getReceiverPk();
				display("recvKey:"+recvKey);
				
				for(User u:users){
					display(recvKey+"="+u.getSenderPk());
					if(recvKey.equals(u.getSenderPk())){
					
					sOutput.writeObject(u.getAmount()+","+u.getReceiverPk());
					u.setAmount(u.getAmount());
					sOutput.writeObject("Updated amount\n"+u.getAmount()+","+u.getReceiverPk());
					}
					else{
						sOutput.writeObject(u.toString());
//						u.setAmount(24);
//						sOutput.writeObject("Updated amount\n"+u.getAmount()+","+u.getReceiverPk());
						
					}
					}
					
				}
//				sOutput.writeObject(msg);
//				sOutput.writeObject("Size:"+transactions.size());
			}
//			 if an error occurs, do not abort just inform the user
			catch(IOException e) {
				display("Error sending message to ");
				display(e.toString());
			}
			return true;
		}*/
			return true;
	}
	}
}

