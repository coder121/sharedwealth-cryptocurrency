import java.io.File;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;


public class Transaction {
	  private static final int version=1;
	    private int listofInput;
	    private int listofOutputs;
	    private String senderPk;
	    private String receiverPk;
	    private double amount;


	    public Transaction( String receiverPK,double amount) {
	        this.receiverPk=receiverPK;
	        this.amount=amount;
	        this.senderPk="akjsak";
	        this.listofInput=1;
	        this.listofOutputs=1;
	    }
	    public String getSenderPk(){
	        return senderPk;
	    }
	    public String getReceiverPk(){
	        return receiverPk;
	    }
	    public double getAmount(){
	        return amount;
	    }
	    public int getListofInput(){
	        return listofInput;
	    }
	    public int getListofOutput(){
	        return listofOutputs;
	    }
	    public void setAmount(double Amount){
	        this.amount=amount;
	    }
	    public void setInputs(){
	        this.listofInput=listofInput;
	    }
	    public int getListofOutputs() {
			return listofOutputs;
		}
	    public void setSenderPk() {
	    	File f=new File("address.txt");
//	    	KeyPair  pair ;
//	    	KeyFactory fact = KeyFactory.getInstance("ECDSA", "BC");
//	    	PublicKey public = fact.generatePublic(new X509EncodedKeySpec(pair.getPublic().getEncoded()));
//	    	PrivateKey private = fact.generatePrivate(new PKCS8EncodedKeySpec(pair.getPrivate().getEncoded()));
	    	
			this.senderPk = senderPk;
		}
	    
	    

}
