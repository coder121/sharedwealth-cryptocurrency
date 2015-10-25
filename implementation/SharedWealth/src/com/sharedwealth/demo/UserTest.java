package com.sharedwealth.demo;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void test() {
		User u=new User();
		System.out.println(u.getAddress());
		System.out.println(u.getAmount());
	
	}


}
