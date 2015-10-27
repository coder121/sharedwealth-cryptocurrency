package com.sharedwealth.networkMining;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.sharedwealth.demo.User2;
import com.sharedwealth.mining.Miner;
import com.sharedwealth.transaction.Transaction;

/*
 * The Client that can be run both as a console or a GUI
 */
public class MinerDemo2  {

	// for I/O
	private ObjectInputStream sInput;		// to read from the socket
	private ObjectOutputStream sOutput;		// to write on the socket
	private Socket socket;

	private String server, username;
	private int port;
	private Miner user;
	/*
	 *  Constructor called by console mode
	 *  server: the server address
	 *  port: the port number
	 *  
	 *  transaction:the transaction
	 */

	MinerDemo2(String server, int port,Miner m) {
		this.server = server;
		this.port = port;
		this.user=m;
		
		
	
	}
	
	/*
	 * To start the dialog
	 */
	public boolean start() {
		// try to connect to the server
		try {
			socket = new Socket(server, port);
		} 
		// if it failed not much I can so
		catch(Exception ec) {
			display("Error connectiong to server:" + ec);
			return false;
		
		}
		
		String msg = "Successfully connected to Bitcoin Network";
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
		return true;

	}

	/*
	 * To send a message to the console or the GUI
	 */
	private void display(String msg) {

			System.out.println(msg);      // println in console mode

	}

	public static void main(String[] args) throws FileNotFoundException {
		// default values
		int portNumber = 1500;
		String serverAddress = "localhost";

		Miner m=new Miner();
		MinerDemo2 client = new MinerDemo2(serverAddress, portNumber, m);
	
		
		
		if(!client.start())return;
	
		client.connect(m);
		
	
		
	}

	private void connect(Miner m) {
	try {
		sOutput.writeObject(m);
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
				int i=0;
				try {
					Transaction t = (Transaction) sInput.readObject();
					
						System.out.print(">");
						System.out.println("Received transaction "+i+" from the Bitcoin Network:\n"+t.toString());
						
						System.out.println("Adding the Transaction to the block");
						user.addTransactionToBlock(t);
						System.out.println("The Size of Block:"+user.getBlockDetails());
						i+=1;
						 
					}
			
					
				
				catch(IOException e) {
					display("Server has close the connection: " + e);
					
					break;
				}
				// can't happen with a String object but need the catch anyhow
				catch(ClassNotFoundException e2) {
				}
				String result=user.getMerkleRoot();
				System.out.println(user.getProofOfWork(result));
//				System.out.println("Merkle Root:\n"+result);
				
			}
		}
	}
}
