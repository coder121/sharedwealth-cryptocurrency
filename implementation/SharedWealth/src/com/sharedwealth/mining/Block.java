package com.sharedwealth.mining;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import com.sharedwealth.transaction.Transaction;



public class Block implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  UUID blockID;
	private final String MaginNo = "0xD9B4BEF9";
	private int blockSize;
    private BlockHeader blockHeader;
	private int transactionCounter;// no of transactions in the block
	private ArrayList<Transaction> transactions; // list of transactions
	private String poofOfWorkSol;// solution of proof of Work
	private String rewardAddress;// address of the winner
	private int MinerID;
	private double elapsedTime;
    
	public Block() {
		transactions = new ArrayList<Transaction>();
		transactionCounter = 0;
		blockHeader=new BlockHeader(transactions);
		blockSize=1;//setting the default size of block to 1
		blockID=UUID.randomUUID();
	}

	public int getBlockSize() {
		return blockSize;
	}
	public void setBlockSize(int size) {
		this.blockSize = size;
	}

	public String getPoofOfWorkSol() {
		return poofOfWorkSol;
	}

	public String getRewardAddress() {
		return rewardAddress;
	}

	public int getTrasactionCounter() {
		return transactionCounter;
	}

	public void setPoofOfWorkSol(String poofOfWorkSol) {
		this.poofOfWorkSol = poofOfWorkSol;
	}

	public void incrementTC() {
		transactionCounter += 1;

	}

	public void setRewardAddress(String rewardAddress) {
		this.rewardAddress = rewardAddress;
	}

	public void addTransaction(Transaction t) {
		transactions.add(t);
		incrementTC();
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public String getMerkRootOfBlock(ArrayList<Transaction> transactions){
		return blockHeader.getHashMerkleRoot(transactions);
	}
	
	public String getBlockHash(){
		return blockHeader.getHashMerkleRoot();
	}
	public UUID getBlockId(){
		return blockID;
	}
	public void setMinerID(int id){
		this.MinerID=id;
	}
	public int getMinerID(){
		return MinerID;
	}
	public void setElapsedTime(double time){
		this.elapsedTime=time;
	}
	public double getElapsedTime(){
		return elapsedTime;
	}
	
	

}
