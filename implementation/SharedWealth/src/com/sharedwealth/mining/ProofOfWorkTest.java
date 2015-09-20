package com.sharedwealth.mining;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class ProofOfWorkTest {

	@Test
	public void poofOfWorkPosSoltest() {
		String header="I am Satoshi Nakamoto";
		String solution=ProofOfWork.compute(header);
		String[] verification=solution.split(":");
		Assert.assertEquals(true,ProofOfWork.Verify(Integer.parseInt(verification[0]), header, verification[1]));
		
	}
	@Test
	public void poofOfWorkNegSoltest() {
		String header="I am Satoshi Nakamoto";
		String solution=ProofOfWork.compute(header);
		String[] verification=solution.split(":");
		Assert.assertEquals(false,ProofOfWork.Verify(Integer.parseInt(verification[0]), header, "34f5082aaef3d66b37a661696c2b618e62432727216ba9531041a5"));
		
	}
	@Test
	public void poofOfWorkNonceTest() {
		String header="I am Satoshi Nakamoto";
		ProofOfWork.setTarget(2);
		String solution=ProofOfWork.compute(header);
		String[] verification=solution.split(":");
		Assert.assertEquals(123,ProofOfWork.getNonce());
		
	}
	@Test
	public void poofOfWorkHighDifficultTest() {
		String header="I am Satoshi Nakamoto";
		ProofOfWork.setTarget(8);
		String solution=ProofOfWork.compute(header);
		String[] verification=solution.split(":");
		Assert.assertEquals("00000000",ProofOfWork.getTarget());
		
	}


}
