package com.sharedwealth.networkMining.Model1;

import java.net.*;
import java.io.*;
import java.util.*;

import com.sharedwealth.demo.User;
import com.sharedwealth.demo.User2;
import com.sharedwealth.transaction.Transaction;

/*
 * The Client that can be run both as a console or a GUI
 */
public class TransactionsDemo2  {

	// for I/O
	private ObjectInputStream sInput;		// to read from the socket
	private ObjectOutputStream sOutput;		// to write on the socket
	private Socket socket;

	private String server, username;
	private int port;
	private User2 user;
	/*
	 *  Constructor called by console mode
	 *  server: the server address
	 *  port: the port number
	 *  username: the username
	 *  transaction:the transaction
	 */

	TransactionsDemo2(String server, int port,User2 u) {
		this.server = server;
		this.port = port;
		this.user=u;
		
		
	
	}
	
	/*
	 * To start the dialog
	 */
	public void start() {
		// try to connect to the server
		try {
			socket = new Socket(server, port);
		} 
		// if it failed not much I can so
		catch(Exception ec) {
			display("Error connectiong to server:" + ec);
		
		}
		
		String msg = "Successfully connected to Bitcoin Network...." ;
		display(msg);
	
		/* Creating both Data Stream */
		try
		{
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
		
		}

		// creates the Thread to listen from the server 
		new ListenFromServer().start();
		// Send our username to the server this is the only message that we
		// will send as a String. All other messages will be ChatMessage objects
		
		// success we inform the caller that it worked

	}

	/*
	 * To send a message to the console or the GUI
	 */
	private void display(String msg) {
//		if(cg == null)
			System.out.println(msg);      // println in console mode
//		else
//			cg.append(msg + "\n");		// append to the ClientGUI JTextArea (or whatever)
	}
	
	/*
	 * To send a message to the server
	 */
	
	

	/*
	 * When something goes wrong
	 * Close the Input/Output streams and disconnect not much to do in the catch clause
	 */
/*	private void disconnect() {
		try { 
			if(sInput != null) sInput.close();
		}
		catch(Exception e) {} // not much else I can do
		try {
			if(sOutput != null) sOutput.close();
		}
		catch(Exception e) {} // not much else I can do
        try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {} // not much else I can do
		
		// inform the GUI
	
			
	}*/
	
	public static void main(String[] args) throws FileNotFoundException {
		// default values
		int portNumber = 1500;
		String serverAddress = "localhost";
//		String userName = "two";
//		Transaction t=new Transaction("bc", 2);
		User2 bob=new User2("user2.wallet");
		bob.createTransaction("1K66fCjQNKWDcxjY9HaFWy9DTF3PM9SwXC", bob.getAddress(), 1.2);
		
		
		
		

	
		// create the Client object
		TransactionsDemo2 client = new TransactionsDemo2(serverAddress, portNumber, bob);
		// test if we can start the connection to the Server
		// if it failed nothing we can do
		
	
		client.start();
	
		client.connect(bob);
		
		// wait for messages from user
		
//		BufferedReader in=new BufferedReader(new InputStreamReader(socke))
		// loop forever for message from the user
		
			
			// read message from user
			
			// logout if message is LOGOUT
		
		
			
			
		
		// done disconnect
		
	}

	private void connect(User2 u) {
	try {
		sOutput.writeObject(u);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}

	/*
	 * a class that waits for the message from the server and append them to the JTextArea
	 * if we have a GUI or simply System.out.println() it in console mode
	 */
	class ListenFromServer extends Thread {

		public void run() {
			while(true) {
				try {
					String msg = (String) sInput.readObject();
					// if console mode print the message and add back the prompt
					System.out.print(">");
						System.out.println(msg);
						
						
					}
					
				
				catch(IOException e) {
					display("Server has close the connection: " + e);
					
					break;
				}
				// can't happen with a String object but need the catch anyhow
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
}
