package com.sharedwealth.demo;

import java.io.File;
import java.io.IOException;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.core.Wallet.*;


public class WalletBit {
	final  NetworkParameters netParams=NetworkParameters.testNet() ;
	private  Wallet wallet = null;
	 final File walletFile=new File("test.wallet");
	 ECKey key;
	 
	 public WalletBit() throws IOException {
		// TODO Auto-generated constructor stub
		  wallet = new Wallet(netParams);
		 for (int i=0;i<5;i++){
			 wallet.addKey(new ECKey());
		 }
		 wallet.saveToFile(walletFile);
	}
	 public void getKey() {
		 System.out.println(wallet);}
	 
	 
}
