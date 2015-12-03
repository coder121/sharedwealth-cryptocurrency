package com.sharedwealth.networkMining.Model3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import com.sharedwealth.demo.User2;
import com.sharedwealth.mining.Block;
import com.sharedwealth.mining.Miner;
import com.sharedwealth.mining.ProofOfWork;
import com.sharedwealth.transaction.Transaction;

/**
 * * 
 * This is sample Bitcoin Network which facilitates transactions 
 * by broadcasting it to the miners connected to this network
 * Miners collect the transactions in the Block and perform 
 * proof of work on the series of transactions they collect in the Block.
 * Upon successfull finding the proof of work they send back the solution to the network 
 * where other miners verify their solution. If solution is valid. The miners are rewarded
 * @author Siddiq Ahmed Syed
 *
 */
public class BitcoinNetwork {
	// a unique ID for each connection
	private static int uniqueId;
	// an ArrayList to keep the list of the Client
	private ArrayList<ClientThread> al;
	private ArrayList<User2> users;//users are the regular users who uses bitcoin on daily basis
	private HashSet<Miner> miner;//miners are people who are responsible for maintaining the bicoin ecosystem
	//transaction pool to keep track of transactions that are added to the Block.
	//where true means the transaction is added to block discovered and
	//false means the transaction is not added to the block
	private HashMap<Transaction, Boolean> transactionPool;
	private SimpleDateFormat sdf;
	private HashMap<Integer, Integer> rewardStructure;
	private HashMap<Integer, String> timeSpent;
	private ArrayList<Block> blockchain;
	private ArrayList<Integer> minerRewardIDs;
	
	private int port;
	// the boolean that will be turned of to stop the server
	private boolean keepGoing;
	private boolean hasRewarded;
	private int rewardCount=1;
	private int uId;
	private String solution;
	private ArrayList<Block> nearMissMiners;
	private int i;
	

	
	public BitcoinNetwork(int port) {
	
		this.port = port;
		// to display hh:mm:ss
		sdf = new SimpleDateFormat("HH:mm:ss");
		// ArrayList for the Client list
		al = new ArrayList<ClientThread>();
		users=new ArrayList<User2>();
		miner=new HashSet<>();
		transactionPool=new HashMap<>();
		rewardStructure=new HashMap<>();
		initializeRewardStructure(6);
		timeSpent=new HashMap<>();
		intializeTimeSpend(6);
		setHasRewarded(false);
		uId=0;
		solution=null;
		minerRewardIDs=new ArrayList<>();
		blockchain=new ArrayList<>();
		nearMissMiners=new ArrayList<>();
		i=1;
		}
	
	private void intializeTimeSpend(int noOfMiners) {
		for(int i=1;i<=noOfMiners;i++)
			timeSpent.put(i, "");
			
		}
		
	

	private void initializeRewardStructure(int noOfMiners) {
		for(int i=1;i<=noOfMiners;i++)
		rewardStructure.put(i, 0);
		
	}

	public void start() {
		keepGoing = true;
		
		try 
		{
			// the socket used by the server
			ServerSocket serverSocket = new ServerSocket(port);

			// infinite loop to wait for connections
			while(keepGoing) 
			{
				// format message saying we are waiting
				display("Bitcoin Network started...");
				display("The difficulty level is:"+ProofOfWork.difficulty);
				Socket socket = serverSocket.accept();  	// accept connection
				// if I was asked to stop
				if(!keepGoing)
					break;
				ClientThread t = new ClientThread(socket);  // make a thread of it
				t.setName(i+"");
				display("Thread Name:"+t.getName());
				i++;
				al.add(t);

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
   	private void display(String msg) {
		String displayMessage = sdf.format(new Date()) + " " + msg;

			System.out.println(displayMessage);
	}
	/*
	 *  to broadcast a message to all Clients
	 */
   /*	
	private synchronized void broadcast(String message) {
		
		String time = sdf.format(new Date());
		String messageLf = time + " " + message + "\n";
	
			System.out.print("This is a test:"+messageLf);
//		
		for(int i = al.size(); --i >= 0;) {
			ClientThread ct = al.get(i);
			// try to write to the Client if it fails remove it from the list
			if(!ct.writeMsg(messageLf)) {
				al.remove(i);
				display("Disconnected Client  removed from list.");
			}
		}
	}*/

	
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
	
	
	public static void main(String[] args) {
		
		int portNumber = 1500;
		BitcoinNetwork server = new BitcoinNetwork(portNumber);
		
		server.start();
	}

	public boolean isHasRewarded() {
		return hasRewarded;
	}

	public void setHasRewarded(boolean hasRewarded) {
		this.hasRewarded = hasRewarded;
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
		private int counter=0;

		// Constructor
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
		

			
            date = new Date().toString() + "\n";
		}

		// what will run forever
		public void run() {
			// to loop until LOGOUT
			
			while(keepGoing) {
				// read a String (which is an object)
				try {
					Object o=sInput.readObject();
                                                 
				    
					//checking if the object is from user 
					if(o instanceof User2){
						display("sending a confirmation message to the user");
					newUser=(User2)o;
						users.add(newUser);//adding users to the arraylist 
						//adding the transaction received into pool of transactions.False mean it is not yet added into the block.
						transactionPool.put(newUser.getTransaction(), false);
//						display(transactionPool.toString());
						sOutput.reset();
						sOutput.writeObject("Bitcoin Network:Successfully connected.Received the following transaction\n>"+newUser.toString());
					     broadcastToMiner(newUser);
					}
				//if the object is a miner than the network sends all the transactions 
				//to the miner. So miner can record them in a block
					else if(o instanceof Block){
						
						display("Here");
						
//						if(counter==blockchain.size()){
						Block b=(Block)o;
			            blockchain.add(b);
//						display("Near Miss:"+b.hasNearMiss);
						//to reward the first miner
						if(b.hasNearMiss){
							if(nearMissMiners.size()<6){
							nearMissMiners.add(b);
							}
//							display("nearMissSIze here:"+nearMissMiners.size());
							if(nearMissMiners.size()==6){
							sendNearMiss();
							}
						}
						
						if(counter==minerRewardIDs.size()){
							minerRewardIDs.add(b.getMinerID());
							rewardStructure.put(b.getMinerID(),rewardStructure.get(b.getMinerID())+1);
							}
					try {
							Thread.sleep(2000);
//														sInput.reset();
						counter=minerRewardIDs.size();
					} catch (InterruptedException e) {
					
					e.printStackTrace();
					}
						
//						display("POW SOl from Block:"+b.getPoofOfWorkSol());
//						display("Block ID:"+b.getBlockId());
//						display("Miner ID:"+b.getMinerID());
//						blockchain.add(b);//adding the block to blockchain
//						timeSpent.put(b.getMinerID(),timeSpent.get(b.getMinerID())+","+b.getElapsedTime());
//						if(counter==minerRewardIDs.size()){
/*						try {
							Thread.sleep(2000);
//							sInput.reset();
							counter=blockchain.size();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
						
						
//					}
						
					}
					else if(o instanceof Miner){
						m=(Miner)o;
//						display("Miner exits:"+miner.contains(m));
//						display("Miner Info:\n>"+m.toString());
						
						miner.add(m);//adding the miner to the list of miners
						
						int transactionCounter=0;
//						display("isHasSolved:"+m.isHasSolvedPOW());
//						display("Miner ArrayList:"+miner.size());
				if(m.isHasSolvedPOW()==false){
//						while(m.getBlockSize()>transactionCounter){
//						display("sending transactions to the miner");
						
					
				/*		for(User2 u:users){
							//we want to send only the complete transactions and we want to send only that transactions 
							//that are not included in the Block(false indicate that transactions are not included in the block)
							if(u.hasReceiverKey()&&transactionPool.get(u.getTransaction())==false){
								sOutput.reset();
								sOutput.writeObject(u.getTransaction());
								transactionCounter++;
							}
							
						}*/
//						}
						
						
						}
					/*	else{
							if(!isHasRewarded()){
								String powSoluion=m.getPwSolution();
								String [] solutionArray=powSoluion.split(":");
								uId=Integer.parseInt(solutionArray[0].split(":")[1]);
								String [] powOfString=solutionArray[solutionArray.length-2].split(":");
								String blockHash=solutionArray[solutionArray.length-1].split(":")[1];
							    display("BlockHash:"+blockHash);
								display("BlockHash:"+m.getBlockHash());
								display("Solution:"+powSoluion);
							    boolean solvedPOW=ProofOfWork.Verify(Integer.parseInt(solutionArray[0]),m.getBlockHash(),solutionArray[1]);
								display("Verification:"+solvedPOW); 
							    if(solvedPOW){
									display("Rewarding the miner");
									uId=m.getId();
									setHasRewarded(true);
								}
							  
								display("Miner's id:"+m.getId()+"\n BlockHash:"+m.getBlockHash()+"\nNonce:"+solutionArray[0]+"\nSolution:"+m.getPwSolution());
						
						}
							else{
								display("Already rewarded");
								display("Miner's id:"+uId);

						
					}
							
						}*/
					}
					//display("BlockChain Size:"+blockchain.size());	
					//blockChainInfo();
					//displayWinnerIDS();
					
				//	display(rewardStructure.toString());
//					display(timeSpent.toString());
					
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
		
		private void sendNearMiss() {
		     display("Here in clientThread");
			for(ClientThread ct:al){
				try {
					
					if(ct.getName().equals("7")){
						  display("Here in 7");
					ct.sOutput.writeObject(nearMissMiners);
					
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			resetNearMiss();
			
		}

		private void resetNearMiss() {
		/*	for(int i=0;i<nearMissMiners.size();i++){
				nearMissMiners.remove(i);
			}*/
			int i=0;
			nearMissMiners.clear();
			display("Nearmiss SIze now:"+nearMissMiners.size());
		}

		private void sendNearMiss(Block b) {
				if(miner.size()>0){
				
				for(ClientThread ct:al){
//					System.out.println("ThreadID:"+ct.getId());
					try {
						ct.sOutput.writeObject(b);
//						ct.sOutput.flush();
//						ct.sOutput.close();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			
		}

		private void displayWinnerIDS() {
			int i=1;
			for(Integer id:minerRewardIDs){
			System.out.println(i+". MinerID:"+id);
			i++;
			}
			
		}

		private void blockChainInfo() {
			System.out.println("BlockChain Size:"+blockchain.size());
		/*	for (Block b: blockchain){
				display("BlockID:"+b.getBlockId()+
						"\nMinerID:"+b.getMinerID()+
						"\nElapsedTime:"+b.getElapsedTime()+
						"\nPOW:"+b.getPoofOfWorkSol());
				
				System.out.println(b.getMinerID()+
						","+b.getElapsedTime());
				
			}*/
			   for (Integer key : timeSpent.keySet()) {
			        System.out.println(key +","+timeSpent.get(key));
			    }
			
			
		}

		private synchronized void  broadcastToMiner(User2 user) {
			
			display("ClentThreadSize:"+al.size());
			Collections.shuffle(al);//to randomize sending the transaction
			Collections.shuffle(al);
			Collections.shuffle(al);
			if(miner.size()>0){
				
				for(ClientThread ct:al){
//					System.out.println("ThreadID:"+ct.getId());
					try {
						
						ct.sOutput.writeObject(user.getTransaction());
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			
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

		
	}
}

