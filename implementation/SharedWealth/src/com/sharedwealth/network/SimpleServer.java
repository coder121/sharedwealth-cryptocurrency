package com.sharedwealth.network;
import java.net.*;
import java.io.*;

import com.sharedwealth.transaction.Transaction;
public class SimpleServer  {

	public static void main(String args[]) {
		int port = 2002;
		try {
		ServerSocket ss = new ServerSocket(port);
		Socket s = ss.accept();
		InputStream is = s.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
//		testobject to = (testobject)ois.readObject();
		Transaction t=(Transaction) ois.readObject();
		if (t!=null){System.out.println(t.getAmount());}
		System.out.println((String)ois.readObject());
		is.close();
		s.close();
		ss.close();
		}catch(Exception e){System.out.println(e);}
		}
}