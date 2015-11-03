package com.sharedwealth.networkMining2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.sharedwealth.demo.User2;
import com.sharedwealth.mining.Miner;
import com.sharedwealth.transaction.Transaction;

/**
 * The Miners collects the transactions into the Block.
 * These are the transactions send to them by the Bitcoin Network
 * The network sends only those transactions that are not included in the Bloc
 *   
 * @author Siddiq Ahmed Syed
 *
 */
public class MinerDemo  {
	private ObjectInputStream sInput;		// to read from the socket
	private ObjectOutputStream sOutput;		// to write on the socket
	private Socket socket;

	private String server;
	private int port;
	private Miner user;
	private int blockSize;
	/*
	 *  
	 *  
	 *  transaction:the transaction
	 */

	MinerDemo(String server, int port,Miner m) {
		this.server = server;
		this.port = port;
		this.user=m;
		blockSize=0;
		
		
	
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

			System.out.println(msg);     

	}

	public static void main(String[] args) throws FileNotFoundException {
		// default values
		int portNumber = 1500;
		String serverAddress = "localhost";

		Miner m=new Miner();
		MinerDemo client = new MinerDemo(serverAddress, portNumber, m);
		m.setBlockSize(2);//setting the blockSize to 2
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
				blockSize=user.getBlockSize();
				try {
					Transaction t = (Transaction) sInput.readObject();
					
						System.out.print(">");
						System.out.println("Received transaction  from the Bitcoin Network:\n"+t.toString());
						
						System.out.println("Adding the Transaction to the block");
						if(user.getCurrentBlockSize()<blockSize){
						user.addTransactionToBlock(t);
						System.out.println("The Size of Block:"+user.getCurrentBlockSize());
						}
						 
					}
			
					
				
				catch(IOException e) {
					display("Server has close the connection: " + e);
					
					break;
				}
				// can't happen with a String object but need the catch anyhow
				catch(ClassNotFoundException e2) {
				}
				if(blockSize==user.getCurrentBlockSize()){
					display("ADDED TRANSACTIONS...CALCULATING PROOF OF WORK FOR THE BLOCK WITH TOTAL BLOCK SIZE OF:"+user.getBlockSize());
					String result=user.getMerkleRoot();
					display(user.toString());
//					display("Has Solved:"+user.isHasSolvedPOW());
					String pow=user.getProofOfWork(result);
					display(user.toString());
					
					System.out.println(pow);
//					user.setHasSolvedPOW(true);
//					display("Has Solved:"+user.isHasSolvedPOW());
					try {
//						sOutput.writeObject("This is from miner");
						sOutput.writeObject(user);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				
			}
		}
	}
}
