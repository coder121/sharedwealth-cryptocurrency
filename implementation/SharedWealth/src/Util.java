import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class with methods that are/can be used by different classes
 * @author Siddiq Ahmed Syed
 *
 */
public class Util {

	/**
	 * Method to return SHA-256 hash
	 * @param text 
	 * @return SHA-256 hash
	 */
public static String getHash(String text) {
	MessageDigest md;
	byte[] digest = null;
	String hash=null;
	try {
		md = MessageDigest.getInstance("SHA-256");
		
		md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
		digest = md.digest();
		 BigInteger bi = new BigInteger(1, digest);
		   hash = (String.format("%0" + (digest.length << 1) + "X", bi)).toLowerCase(); //converting bytes to string
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return hash;
}
public static String getDoubleHash(String text) {
	MessageDigest md;
	MessageDigest md2;
	byte[] digest = null;
	byte[] finalDigest = null;
	String hash=null;
	try {
		md = MessageDigest.getInstance("SHA-256");
		md2=MessageDigest.getInstance("SHA-256");
		md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
		digest = md.digest();
		md2.update(digest);
		finalDigest=md2.digest();
		 BigInteger bi = new BigInteger(1, finalDigest);
		   hash = (String.format("%0" + (finalDigest.length << 1) + "X", bi)).toLowerCase(); //converting bytes to string
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return hash;
}

}