package com.sharedwealth.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;

import com.sharedwealth.transaction.Transaction;
import com.sharedwealth.util.Util;


public class User2 implements Serializable {

	private static final long serialVersionUID = 1L;

private File wallet;
transient Scanner in;
private String address;
private double amount;
transient FileWriter fw;
private boolean receiver;//this is to indentify if there is a receiver
private Transaction t;



public User2(String walletName)  {
	
	wallet=new File(walletName);
	//this tells if the user is an existing bitcoin user.
	if(wallet.exists()){
		try {
			in=new Scanner(wallet);
			setAddress(in.nextLine());
			setAmount(Double.parseDouble(in.nextLine()));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	else{
		createWallet();
		setReceiverFlag(false);
		
	}


}

public void setReceiverFlag(boolean flag) {
	this.receiver=flag;
	
}

private void createWallet() {
	try {
		 fw=new FileWriter(wallet);
		
		String address=Util.generateAddress();
		setAddress(address);
		fw.write(address);
		setAmount(0.0);
		fw.write("\n0.0");
		fw.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}

public void setAmount(double amnt) {
 this.amount=amnt;
 }



public String getAddress() {
	return address;
}

public void setAddress(String address) {
	
	this.address = address;
}

public double getAmount() {
	// TODO Auto-generated method stub
	return amount;
}
public boolean hasReceiverKey(){
	return receiver;
}

public void createTransaction(String receiverPubKey,String senderPubKey,double amnt) throws FileNotFoundException{
	t=new Transaction(receiverPubKey, senderPubKey, amnt);
	setReceiverFlag(true);
	
	
}
/**
 * This method will return the transaction object 
 * This is used by miner
 * @return transaction object
 */
public Transaction getTransaction(){
	return t;
}

public String toString(){
	if(hasReceiverKey()){
		return t.toString();
	}
	else{
		return "PubKey:"+getAddress()+"\nAmount:"+getAmount();
	}
}


}