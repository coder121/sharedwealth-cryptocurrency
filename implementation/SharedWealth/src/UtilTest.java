import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;


public class UtilTest {

	@Test
	public void getHashTest() {
		String hash=Util.getHash("hello");
		Assert.assertEquals("2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824", hash);
				
	}
	@Test
	public void getDoubleHashTest() {
		String hash=Util.getDoubleHash("hello");
		Assert.assertEquals("9595c9df90075148eb06860365df33584b75bff782a510c6cd4883a419833d50", hash);
				
	}
	@Test
	public void nonceHashTest(){
		String sentence="I am Satoshi Nakamoto";
		for(int i=0;i<16;i++){
			String newSent=sentence+i;
			String hash=Util.getHash(newSent);
			System.out.println(newSent+"=>"+hash);
		}
	}

}
