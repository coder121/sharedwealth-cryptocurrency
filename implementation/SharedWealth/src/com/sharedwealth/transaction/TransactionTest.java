package com.sharedwealth.transaction;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TransactionTest {

	@Test
	public void setTransactionTest() throws FileNotFoundException {

		Transaction t = new Transaction("aras", 1);
		 List<Integer> lst = new LinkedList<Integer>();
		 for (int i = 1; i <= 5; i++) lst.add(i);
		 System.out.println(lst);
	      for (int i = 1; i < 5; i++) lst.remove(0);
		 System.out.println(lst);
		Assert.assertEquals("xyzea", t.getSenderPk());
		

	}

}
