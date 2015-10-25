package com.sharedwealth.transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Transaction implements java.io.Serializable {
	private static final int version = 1;
	private int listofInput;
	private int listofOutputs;
	private String senderPk;
	private String receiverPk;
	private double amount;


	public Transaction(String receiverPK, String senderPk,double amount)  throws FileNotFoundException {

		this.receiverPk = receiverPK;
		this.amount = amount;
		this.senderPk=senderPk;
		
		this.listofInput = 1;
		this.listofOutputs = 1;
	}
	public Transaction() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * This is another constructor that basically assigns 
	 * the senderAddress
	 * This is used to connect to the server 
	 * and make server aware of your existence
	 *  
	 * 
	 */


	public String getSenderPk() {
		return senderPk;
	}

	public String getReceiverPk() {
		return receiverPk;
	}

	public double getAmount() {
		return amount;
	}

	/*
	 * public int getListofInput(){ return listofInput; } public int
	 * getListofOutput(){ return listofOutputs; }
	 */
	public void setAmount(double Amount) {
		this.amount = amount;
	}

	public void setInputs() {
		this.listofInput = listofInput;
	}

	public void setReceiverPk(String receiverPk) {

		this.receiverPk = receiverPk;
	}

	public void setSenderPk(String senderPk) {
		this.senderPk = senderPk;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "SenderPubKey:"+getSenderPk()+"\nReceiverPubKey:"+getReceiverPk()+"\nAmount:"+getAmount();
	}
}
