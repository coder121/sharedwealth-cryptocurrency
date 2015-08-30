public class Transaction {
	private static final int version = 1;
	private int listofInput;
	private int listofOutputs;
	private String senderPk;
	private String receiverPk;
	private double amount;

	public Transaction(String receiverPK, double amount) {
		this.receiverPk = receiverPK;
		this.amount = amount;
		this.senderPk = "akjsak";
		this.listofInput = 1;
		this.listofOutputs = 1;
	}

	public String getSenderPk() {
		return senderPk;
	}

	public String getReceiverPk() {
		return receiverPk;
	}

	public double getAmount() {
		return amount;
	}

	/*
	 * public int getListofInput(){ return listofInput; } public int
	 * getListofOutput(){ return listofOutputs; }
	 */
	public void setAmount(double Amount) {
		this.amount = amount;
	}

	public void setInputs() {
		this.listofInput = listofInput;
	}

	public void setReceiverPk(String receiverPk) {

		this.receiverPk = receiverPk;
	}

	public void setSenderPk(String senderPk) {
		this.senderPk = senderPk;
	}
}
