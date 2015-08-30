import java.util.ArrayList;


public class BlockChain {
	private final String MaginNo="0xD9B4BEF9";
	private double blockSize;
	//private BlockHeader blockHeader;
	private int trasactionCounter=0;//no of transactions in the block
	private ArrayList<Transaction> transactions; //list of transactions
	 private String  poofOfWorkSol;//solution of proof of Work
	 private String rewardAddress;//address of the winner
	 
	 public BlockChain() {
		// TODO Auto-generated constructor stub
	}
	 public double getBlockSize() {
		return blockSize;
	}public String getMaginNo() {
		return MaginNo;
	}
	public String getPoofOfWorkSol() {
		return poofOfWorkSol;
	}
	public String getRewardAddress() {
		return rewardAddress;
	}
	public int getTrasactionCounter() {
		return trasactionCounter;
	}public void setPoofOfWorkSol(String poofOfWorkSol) {
		this.poofOfWorkSol = poofOfWorkSol;
	}
	
	public void incrementTC(){
		trasactionCounter+=1;
		
	}
	
	
	public void setRewardAddress(String rewardAddress) {
		this.rewardAddress = rewardAddress;
	}
	

}
