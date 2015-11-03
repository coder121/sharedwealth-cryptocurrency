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
	
	
	/**
	 * 
	 */
UUID uuid;

	private Block block;
	
	 private ProofOfWork pw;
	 private static boolean hasSolvedPOW=false;
	/*
	 *  Constructor called by console mode
	 *  server: the server address
	 *  port: the port number
	 *  username: the username
	 *  transaction:the transaction
	 */

	public Miner() {
		
		block=new Block();
	
		uuid=UUID.randomUUID();
		
		}
	
	public UUID getUUID() {
		return uuid;
	}
	
public void addTransactionToBlock(Transaction t){
	block.addTransaction(t);
}

public int getCurrentBlockSize(){
	return block.getTrasactionCounter();
}
public int getBlockSize(){
	return block.getBlockSize();
}
public String getMerkleRoot(){
	
	return block.getMerkRootOfBlock(block.getTransactions());
}
public String getProofOfWork(String header){
	setHasSolvedPOW(true);
	return ProofOfWork.compute(header);
}
public void setBlockSize(int size){
	block.setBlockSize(size);
}

public static boolean isHasSolvedPOW() {
	return hasSolvedPOW;
}
public void setHasSolvedPOW(boolean flag) {
	hasSolvedPOW = flag;
}
public String toString(){
	
	return"Id:"+getUUID()+"\nSolved:"+isHasSolvedPOW()+"\nBlockSize:"+getCurrentBlockSize();
	
	
}


	
	
}
