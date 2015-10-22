package com.sharedwealth.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.sharedwealth.transaction.Transaction;
public class SimpleClient {
public static void main(String args[]){

try{
	
Socket s = new Socket("localhost",9001);
OutputStream os = s.getOutputStream();
ObjectOutputStream oos = new ObjectOutputStream(os);
testobject to = new testobject(1,"object from client");
Transaction t=new Transaction("12421981",1221.56);
BufferedReader in = new BufferedReader(new InputStreamReader(
        s.getInputStream()));
//oos.writeObject(new String("whats up"));
oos.writeObject(t);

	String line=in.readLine();
	System.out.println(line);
	

//oos.writeObject(to);
//oos.writeObject(new String("another object from the client"));


}catch(Exception e){System.out.println(e);}
}

class listernServer  extends Thread{
	// TODO Auto-generated method stub
	public void run(){
		
	}
	
}


}


