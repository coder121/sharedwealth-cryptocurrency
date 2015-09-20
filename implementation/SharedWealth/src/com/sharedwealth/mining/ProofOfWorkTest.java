package com.sharedwealth.mining;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProofOfWorkTest {

	@Test
	public void test() {
		String header="I am Satoshi Nakamoto";
		String solution=ProofOfWork.compute(header);
		String[] verification=solution.split(":");
		for(String s:verification){
			System.out.println(s);
		}
		
	}

}
