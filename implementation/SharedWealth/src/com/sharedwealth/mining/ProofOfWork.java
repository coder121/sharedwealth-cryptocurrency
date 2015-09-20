package com.sharedwealth.mining;

import com.sharedwealth.util.Util;

public class ProofOfWork {
	
	int target=1;
	static String solution;
	
	public static String getSolution() {
		return solution;
	}
	public static String compute(String header){
	    int nonce=0;
	    Boolean flag=true;
	    while(flag){		
	    String newHeader=header+nonce;
	    
		//String result=Util.getDoubleHash(newHeader);
	    String result=Util.getHash(newHeader);
	    
		if(result.charAt(0)=='0'){
			
			System.out.println("Found it\n"+"Nonce:"+nonce+"\nResult:"+result);
			flag=false;
			return nonce+":"+result;
		}
		nonce++;
	    }
		return null;
		
	}
	public static boolean Verify(int nonce,String header,String hashResult){
		String verification=Util.getHash(header+nonce);
		if(verification.equals(hashResult)){
			return true;
		}
		return false;
		
	}

}
