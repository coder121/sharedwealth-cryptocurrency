import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;

public class ProofOfWork {
	private byte[] nonce;
	private Random rand;
	private String hash;// resultant hash
	private int difficulty = 1;// only one zer0
	private boolean found = false;// flag for result

	public ProofOfWork(Block b) {
		nonce = new byte[4];// 32bit
		compute(b);
	}

	private void compute(Block b) {

		String n = getNonce();

	}

	private String getNonce() {
		String n = null;
		try {
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");

			sr.nextBytes(nonce);
			n = new String(Hex.encodeHex(nonce));

		} catch (Exception e) {
			e.getMessage();
		}
		return n;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public String getHash() {
		return hash;
	}

	public boolean isFound() {
		return found;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

}
