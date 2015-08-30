import org.junit.Assert;
import org.junit.Test;

public class TransactionTest {

	@Test
	public void setTransactionTest() {

		Transaction t = new Transaction("aras", 1);
		Assert.assertEquals(1.0, t.getAmount(), 0);

	}

}
