package com.sharedwealth.network;

import java.io.FileOutputStream;  
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
import java.io.PrintWriter;
import java.net.ServerSocket;  
import java.net.Socket;  

import com.sharedwealth.transaction.Transaction;
  
public class BitcoinServer2 extends Thread {  
    public static final int PORT = 3332;  
    public static final int BUFFER_SIZE = 100;  
  
    @Override  
    public void run() {  
        try {  
            ServerSocket serverSocket = new ServerSocket(PORT);  
  
            while (true) {  
                Socket s = serverSocket.accept();  
                getUser(s);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    private void getUser(Socket socket) throws Exception {  
    	System.out.println("i am here now");
       // ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());  
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());  
//        PrintWriter pw=new PrintWriter(socket.getOutputStream(),true);
//        FileOutputStream fos = null;  
        String pubKey=null;
        String recvKey=null;
//        byte [] buffer = new byte[BUFFER_SIZE];  
  
        // 1. Read file name.  
        
        Object o = ois.readObject();  
//        System.out.println("PubKey"+pubKey);
        if (o instanceof Transaction) {  
            pubKey= ((Transaction) o).getSenderPk();
            recvKey=((Transaction) o).getReceiverPk();
            System.out.println("PubKey:"+pubKey);
            System.out.println("Rec Key:"+recvKey);
        } else {  
            throwException("Something is wrong");  
        }  
  
//       pw.println("PubKey"+pubKey);
//       System.out.println("PubKey"+pubKey);
       
            
        
          

  
        ois.close();  
//        oos.close();  
    }  
  
    public static void throwException(String message) throws Exception {  
        throw new Exception(message);  
    }  
  
    public static void main(String[] args) {  
        new BitcoinServer2().start();  
    }  
}    