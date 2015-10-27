package com.sharedwealth.demo;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class UserTest {

	@Test
	public void test() throws InterruptedException {
		User u=new User("user1.walletTest");
	
		System.out.println(u.getAddress());
		Assert.assertEquals(0.0, u.getAmount());
	
	}


}
