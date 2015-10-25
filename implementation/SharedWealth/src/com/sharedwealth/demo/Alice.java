package com.sharedwealth.demo;

import java.io.FileNotFoundException;

import com.sharedwealth.transaction.Transaction;

public class Alice  {
	
public static void main(String ...args) throws FileNotFoundException {
	User u=new User();
	Transaction t=new Transaction("asamas", u.getAddress(), 212);
	System.out.println(t.toString());
	
}

}
