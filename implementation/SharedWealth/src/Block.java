import java.util.ArrayList;

import com.sharedwealth.transaction.Transaction;

public class Block {
	private final String MaginNo = "0xD9B4BEF9";
	private double blockSize;
    private BlockHeader blockHeader;
	private int transactionCounter;// no of transactions in the block
	private ArrayList<Transaction> transactions; // list of transactions
	private String poofOfWorkSol;// solution of proof of Work
	private String rewardAddress;// address of the winner

	public Block() {
		transactions = new ArrayList<Transaction>();
		transactionCounter = 0;
	}

	public double getBlockSize() {
		return blockSize;
	}

	public String getPoofOfWorkSol() {
		return poofOfWorkSol;
	}

	public String getRewardAddress() {
		return rewardAddress;
	}

	public int getTrasactionCounter() {
		return transactionCounter;
	}

	public void setPoofOfWorkSol(String poofOfWorkSol) {
		this.poofOfWorkSol = poofOfWorkSol;
	}

	public void incrementTC() {
		transactionCounter += 1;

	}

	public void setRewardAddress(String rewardAddress) {
		this.rewardAddress = rewardAddress;
	}

	public void addTransaction(Transaction t) {
		transactions.add(t);
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

}
