import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;


public class UtilTest {

	@Test
	public void getHashTest() {
		String hash=Util.getHash("hello");
		Assert.assertEquals("2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824", hash);
				
	}

}
