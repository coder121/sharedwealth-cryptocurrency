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
public class MinerDemoSW2  {

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

	MinerDemoSW2(String server, int port,SWMiner m) {
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
		MinerDemoSW2 client = new MinerDemoSW2(serverAddress, portNumber, m);
		m.setBlockSize(1);//setting the block size to 3
		m.setId(6);
		
	
		
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
					
					if(o instanceof Block){
						Block b=(Block)o;
					    if(nearMiss.size()==0){
					    	nearMiss.add(b);
					    }
						if(nearMiss.size()>0&&isUnique(b)){
					    nearMiss.add(b);
						}
						if(nearMiss.size()==1){
						calcularePOW();
						}

					
						
					}
					if(o instanceof ArrayList<?>){
				    
				     
					@SuppressWarnings("unchecked")
					ArrayList<Block> nearMissMiners=(ArrayList<Block>) o;
					
				      calcularePOW(nearMissMiners);
				    /*  for(Block b:nearMiss){
				    	  display(b.getMinerID()+":"+b.getNearMissValue());
				      }*/
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

		private void calcularePOW(ArrayList<Block> nearMissMiners) {
	     
	     Block high=getMaxNearMissMiner(nearMissMiners);
//	     display("Headers:"+high.getBlockHash());
	   
	     
	     int nonce=Integer.parseInt(high.getPoofOfWorkSol().split(":")[0]);
//	     display("Start Nonce:"+nonce);
			ProofOfWork.compute(high.getBlockHash(),high.getNearMissValue());
		display("Time:"+ProofOfWork.elapsedTime);	
		}

		private Block getMaxNearMissMiner(ArrayList<Block> nearMissMiners) {
			System.out.println("minerId,NearMiss,ElapsedTime");
			  Block b=nearMissMiners.get(0);
			  System.out.println(b.getMinerID()+":"+b.getNearMissValue()+","+b.getElapsedTime());
			     for(int i=1;i<nearMissMiners.size();i++){
			    	  System.out.println(nearMissMiners.get(i).getMinerID()+":"+nearMissMiners.get(i).getNearMissValue()+","+nearMissMiners.get(i).getElapsedTime());
			    	 if(b.getNearMissValue()<nearMissMiners.get(i).getNearMissValue()){
			    		
			    		 b=nearMissMiners.get(i);
			    	 }
			     }
			     return b;
			
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
		String nearMissMinerId="";
		for(Block b:nearMiss){
			nearMissMinerId+=b.getMinerID()+","+b.getNearMissValue()+","+b.getElapsedTime()+"\n";
//			nearMiss.remove(b);
		}
		resetNearMiss();
		    display("MinerId:NearMiss,ElapsedTime\n"+nearMissMinerId);
		    
		}

		private void resetNearMiss() {
	for(int i=0;i<nearMiss.size();i++){
		nearMiss.remove(i);
	}
			
		}
		

		
			
		}

	

			
		
 
				
				

		
}

