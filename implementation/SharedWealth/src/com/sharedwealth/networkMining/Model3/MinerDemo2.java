package com.sharedwealth.networkMining.Model3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.sharedwealth.demo.User2;
import com.sharedwealth.mining.Block;
import com.sharedwealth.mining.Miner;
import com.sharedwealth.mining.ProofOfWork;
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
	int blockSize;
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
		this.blockSize=0;
		
		
	
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
		
		String msg = "Successfully connected to Bitcoin Network,MinerId:"+user.getId();
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
		m.setBlockSize(1);//setting the block size to 3
		m.setId(2);
		
	
		
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
					Object o=sInput.readObject();
					
					if(o instanceof Transaction){
					Transaction t = (Transaction)o;
					System.out.println(blockSize+"<"+user.getCurrentBlockSize());
					
						System.out.print(">");
						System.out.println("Received transaction from the Bitcoin Network:\n"+t.toString());
						if(user.getCurrentBlockSize()<blockSize){
						System.out.println("Adding the Transaction to the block");
						user.addTransactionToBlock(t);
						System.out.println("The Size of Block:"+user.getCurrentBlockSize());
					
						
					}
						if(blockSize==user.getCurrentBlockSize()){
							display("ADDED TRANSACTIONS...CALCULATING PROOF OF WORK FOR THE BLOCK WITH TOTAL BLOCK SIZE OF:"+user.getBlockSize());
							String result=user.getMerkleRoot();
							String pow=user.getNearMiss(result,1);
							user.getBlock().setNearMiss(1);
							user.setHasSolvedPOW(true);
							System.out.println(pow);
							
							user.getBlock().setMinerID(user.getId());
							user.getBlock().setHasNearMiss(true);	
							sOutput.reset();
							display("sending the near miss Block");
							display("BLOCK ID:"+user.getBlock().getBlockId());
							sOutput.writeObject(user.getBlock());
								display("resetting the blockSize");
								user.resetBlockSize();
								user.setBlockSize(2);
								Thread.sleep(1000);
						
				}
						
					
					}
					if(o instanceof Miner){
						Miner m=(Miner)o;
						display("Verifying other Miners Work\n");
						String powSoluion=m.getPwSolution();
						String [] solutionArray=powSoluion.split(":");
						/*uId=Integer.parseInt(solutionArray[0].split(":")[1]);
						String [] powOfString=solutionArray[solutionArray.length-2].split(":");
						String blockHash=solutionArray[solutionArray.length-1].split(":")[1];
					    display("BlockHash:"+blockHash);*/
						display("Miners Id:"+m.getId());
						display("Solution:"+powSoluion);
					    boolean solvedPOW=ProofOfWork.Verify(Integer.parseInt(solutionArray[0]),m.getBlockHash(),solutionArray[1]);
						display("Verification:"+solvedPOW); 
					
					
					
					}
					if(o instanceof String){
						display("Message from network:"+o.toString());
					}
					if(o instanceof Block){}
					
				
				}
				catch(IOException e) {
					display("Server has close the connection: " + e);
					
					break;
				}
				// can't happen with a String object but need the catch anyhow
				catch(ClassNotFoundException e2) {
				}
//				display("Block Size:"+blockSize+"i value:"+i);
 catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
					
				}
				} 
				}
				

		
}

