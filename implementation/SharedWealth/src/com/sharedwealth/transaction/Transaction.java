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
	transient Scanner in;

	public Transaction(String receiverPK, double amount)  throws FileNotFoundException {
		in=new Scanner(new File("info.txt"));//file containing public key and amount
		this.receiverPk = receiverPK;
		this.amount = amount;
		this.senderPk = in.next();
		this.listofInput = 1;
		this.listofOutputs = 1;
	}

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
		return getSenderPk()+":"+getAmount()+":"+getReceiverPk();
	}
}
