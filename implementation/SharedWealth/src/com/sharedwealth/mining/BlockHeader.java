package com.sharedwealth.mining;


import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import com.sharedwealth.transaction.Transaction;
import com.sharedwealth.util.Util;
 
 
public class BlockHeader implements Serializable {
	private int blockVersionNo;
	private byte[] hashPrevBlock;
	private byte[] hashMerkleRoot;//hash of all the hashes 
	private SimpleDateFormat dateFormat;
	private byte[] difficulty;
	private int nonce=0;
	private String dateTime;
	ArrayList<Transaction> transactions;
	
public BlockHeader(ArrayList<Transaction> transactions) {
	this.transactions=transactions;
	blockVersionNo=1;
	dateFormat=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	dateTime = dateFormat.format(new java.util.Date());
	dateTime=dateTime.toString();
	
	
}


public void setNonce(int nonce) 
{
	this.nonce = nonce;
}
public void setHashMerkleRoot(byte[] hashMerkleRoot)
{
	this.hashMerkleRoot = hashMerkleRoot;
}public int getBlockVersionNo() 
{
	return blockVersionNo;
}
public String getDateTime()
{
	return dateTime;
}
public String getHashMerkleRoot(ArrayList<Transaction> transactions) {
	String merkleRoot=null;
	for (Transaction t:transactions){
		String hash=Util.getHash(t.toString());
		merkleRoot+=hash;
	}
	merkleRoot=Util.getHash(merkleRoot);
	return new String(merkleRoot);
}

	

	
	


public String getHashPrevBlock() {
	return new String(hashPrevBlock);
}
public String getDifficulty()
{
	return new String(difficulty);
}


@Override
public String toString() {
	// TODO Auto-generated method stub
	return getBlockVersionNo() +":"+getHashPrevBlock()+":"+getHashMerkleRoot(transactions)+":"+getDateTime()+getDifficulty();
}	

}
