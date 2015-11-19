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


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Block block;
	UUID uid;
//	 private ProofOfWork pw;
	 private  boolean hasSolvedPOW;
	 private double balance;
	 private String pwSolution;
	 private String blockHash;






	public Miner() {
		
		block=new Block();
		uid=UUID.randomUUID();
		id=1;
		hasSolvedPOW=false;
		balance=0;
		
		}
	

	public void setPwSolution(String pwSolution) {
		this.pwSolution = pwSolution;
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
	String proofWorkSol=ProofOfWork.compute(header);
	block.setPoofOfWorkSol(proofWorkSol);
	setBlockHash(header);
	setPwSolution(proofWorkSol);
	block.setElapsedTime(ProofOfWork.elapsedTime);
	
	return proofWorkSol;
}
public void setBlockSize(int size){
	block.setBlockSize(size);
}

public boolean isHasSolvedPOW() {
	return hasSolvedPOW;
}
public void setHasSolvedPOW(boolean flag) {
	hasSolvedPOW = flag;
}

public void reward(int amount) {
	
	this.balance+=amount;
}


public double getBalance() {
	return balance;
}
public String getPwSolution() {
	return pwSolution;
}
public void setId(int id) {
	this.id = id;
}
public int getId() {
	return id;
}
public String getBlockHash() {
	return blockHash;
}
public void setBlockHash(String blockHash) {
	this.blockHash = blockHash;
}

public String toString(){
	
	return"Id:"+getId()+"\nSolved:"+isHasSolvedPOW()+"\nBlockSize:"+getCurrentBlockSize()+"\nSolution:"+getPwSolution()+"\nBlockHash:"+getBlockHash();
	
	
}


public UUID getUUID() {
	// TODO Auto-generated method stub
	return uid;
}
public void setUid(UUID uid) {
	this.uid = uid;
}
public Block getBlock(){
	return block;
}


public void resetBlockSize(){
	block.setBlockSize(0);
}


	
	
}
