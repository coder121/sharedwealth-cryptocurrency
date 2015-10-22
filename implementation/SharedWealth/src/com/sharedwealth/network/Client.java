package com.sharedwealth.network;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
import java.net.Socket;  
import java.util.Arrays;  
import java.lang.*;  
import java.util.Scanner;  

import com.sharedwealth.transaction.Transaction;
  
  
public class Client  {  
    public static void main(String[] args) throws Exception {  
       
           
     Transaction t=new Transaction("asa",121);
        Socket socket = new Socket("localhost", 3332);  
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());  
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());  
        
        oos.writeObject(t);  
  
     
  
      
  
        oos.close();  
        ois.close();  
        System.exit(0);      
}  
  
}  
  

