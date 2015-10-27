package com.sharedwealth.mining;

import java.net.*;
import java.io.*;
import java.util.*;

import com.sharedwealth.demo.User;
import com.sharedwealth.demo.User2;
import com.sharedwealth.transaction.Transaction;

/*
 * The Client that can be run both as a console or a GUI
 */
public class Miner implements Serializable {

	// for I/O
	private ObjectInputStream sInput;		// to read from the socket
	private ObjectOutputStream sOutput;		// to write on the socket
	private Socket socket;
	private Block block;
	private String server;
	private int port;
	 private Transaction t;
	 private ProofOfWork pw;
	/*
	 *  Constructor called by console mode
	 *  server: the server address
	 *  port: the port number
	 *  username: the username
	 *  transaction:the transaction
	 */

	public Miner() {
		
		block=new Block();
		pw=new ProofOfWork();
		}
	
public void addTransactionToBlock(Transaction t){
	block.addTransaction(t);
}

public int getBlockDetails(){
	return block.getTrasactionCounter();
}
public String getMerkleRoot(){
	
	return block.getMerkRootOfBlock(block.getTransactions());
}
public String getProofOfWork(String header){
	return pw.compute(header);
}

	
	
}
