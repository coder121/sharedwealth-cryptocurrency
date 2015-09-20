package com.sharedwealth.mining;

import java.lang.annotation.Target;

import com.sharedwealth.util.Util;
/**
 * Class to compute and verify the proof of work.
 * @author Siddiq Ahmed Syed
 *
 */
public class ProofOfWork {
	
	static int difficulty=1;//This specify the target
	static int resultantNonce=0;

	
/**
 * Change the difficulty level
 * @param difficultLevel 
 */
public static void setTarget(int difficultLevel) {
	difficulty = difficultLevel;
}
/**
 * Returns the nonce 
 * @return
 */
public static int getNonce() {
	return resultantNonce;
}
/**
	 * Computes the proof of work based on the target.
	 * @param Block header 
	 * @return If the solution is found that it returns a string
	 *  consisting of the nonce and the result(hash) else it return null
	 */
	public static String compute(String header){
		double startTime=System.currentTimeMillis();
		int nonce=0;
	    Boolean flag=true;
	    Boolean found=false;
	    while(flag){		
	    String newHeader=header+nonce;
	    
		//String result=Util.getDoubleHash(newHeader);
	    String result=Util.getHash(newHeader);
	    
	    
		if(result.startsWith(getTarget())){
			double stopTime=System.currentTimeMillis();
			double elapsedTime=stopTime-startTime;//finding the totalTime
			System.out.println("Solution:\n"+"Nonce:"+nonce+"\nResult:"+result);
			System.out.println("Elapsed Time:"+(elapsedTime/1000)+"s");
			flag=false;
			resultantNonce=nonce;
			return nonce+":"+result;
		}
		nonce++;
	    }
	   return null;
		
	}
	/**
	 * This method creates a target string i.e String consisting of zeros based
	 * on difficulty 
	 * @return targetSring-string consisting of 0s based on difficulty
	 */
	public static String getTarget() {
	String targetString="";
	for(int i=1;i<= difficulty;i++){
		targetString+="0";
		}
	//System.out.println("Target:"+targetString);
	return targetString;
}
	/**
	 * This method is to verify the work done by the miner. 
	 * It verifies whether the solution found by the miner is valid
	 * @param nonce-value used for finding the solution
	 * @param header-Block Header
	 * @param hashResult-proof of work solution
	 * @return true-if valid solution
	 * 		false-if the solution is invalid
	 */
	public static boolean Verify(int nonce,String header,String hashResult){
		String verification=Util.getHash(header+nonce);
		if(verification.equals(hashResult)){
			return true;
		}
		return false;
		
	}
	
	

}
