package com.sharedwealth.networkMining.Model3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import com.sharedwealth.mining.Block;
import com.sharedwealth.mining.Miner;
import com.sharedwealth.mining.ProofOfWork;
import com.sharedwealth.mining.SWMiner;

/*
 * The Client that can be run both as a console or a GUI
 */
public class MinerDemoSW1  {

	// for I/O
	private ObjectInputStream sInput;		// to read from the socket
	private ObjectOutputStream sOutput;		// to write on the socket
	private Socket socket;

	private String server, username;
	private int port;
	private SWMiner user;
	int blockSize;
	private ArrayList<Block> nearMiss;
	/*
	 *  Constructor called by console mode
	 *  server: the server address
	 *  port: the port number
	 *  
	 *  transaction:the transaction
	 */

	MinerDemoSW1(String server, int port,SWMiner m) {
		this.server = server;
		this.port = port;
		this.user=m;
		this.blockSize=0;
		
		nearMiss=new ArrayList<>();
	
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

		SWMiner m=new SWMiner();
		MinerDemoSW1 client = new MinerDemoSW1(serverAddress, portNumber, m);
		m.setBlockSize(1);//setting the block size to 3
		m.setId(4);
		
	
		
		if(!client.start())return;
	
		client.connect(m);
		
		
	
		
	}

	private void connect(SWMiner m) {
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
					
				/*	if(o instanceof Transaction){
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
							String pow=user.getProofOfWork(result);
							user.setHasSolvedPOW(true);
							System.out.println(pow);
							
							user.getBlock().setMinerID(user.getId());
								
							sOutput.reset();
							display("sending the Block for verification");
							display("BLOCK ID:"+user.getBlock().getBlockId());
							sOutput.writeObject(user.getBlock());
								display("resetting the blockSize");
								user.resetBlockSize();
								user.setBlockSize(2);
								Thread.sleep(1000);
						
				}
						
					
					}*/
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
					if(o instanceof Block){
						Block b=(Block)o;
					    if(nearMiss.size()==0){
					    	nearMiss.add(b);
					    }
						if(nearMiss.size()>0&&isUnique(b)){
					    nearMiss.add(b);
						}
						if(nearMiss.size()==5){
						calcularePOW();
						}
//						HashMap< Integer, Integer> minersWork=new HashMap<>();
//						minersWork.put(Integer.parseInt(nonce), nearMiss);
						
                            
//						if(nearMiss<ProofOfWork.difficulty &&user.getBlock().getNearMissIds().size()<=3 ){
//							
//							user.getBlock().addNearMissMiners(b, minersWork);
//							display("Size of miner Reward:"+user.getBlock().getNearMissIds().size());
//							//we want to calculate the POW based near miss send by other Miners
//								getAcutalPOW(user.getBlock().getNearMissIds());
//						}
					
						
					}
					
				
				}
				catch(IOException e) {
					display("Server has close the connection: " + e);
					
					break;
				}
				// can't happen with a String object but need the catch anyhow
				catch(ClassNotFoundException e2) {
				}
//				display("Block Size:"+blockSize+"i value:"+i);

				
					
				}
				}

		private boolean isUnique(Block newBlock) {
			// TODO Auto-generated method stub
			for(Block b:nearMiss){
				if(b.getMinerID()==newBlock.getMinerID())
					return false;
				
			}
			return true;
		}

		private void calcularePOW() {
		display("Near Mis Size:"+nearMiss.size());
		for(Block b:nearMiss){
			display("Miner id:"+b.getMinerID());
			display("Effort id:"+b.getNearMissValue());
			String sol=b.getPoofOfWorkSol();
			int nonce=Integer.parseInt(sol.split(":")[0]);
//			display("nonce:"+nonce);
			ProofOfWork.compute(b.getBlockHash(),nonce);//using the nonce of other miner
			double usingOthers= ProofOfWork.elapsedTime;
			
		ProofOfWork.compute(b.getBlockHash());
			double individualWork=ProofOfWork.elapsedTime;
			
			display("With Others Help:"+usingOthers+"\n Individual Mining"+individualWork);
			
			
		}
		}

		
			
		}

	

			
		
 
				
				

		
}

