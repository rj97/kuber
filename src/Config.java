//import java.nio.charset.StandardCharsets;

public class Config{
	private String key = "abcdefghijklmnopqrstuvwxyzabcdef";	//Should be 32 bits. Update as required.
    
    private String nonce = "abcdefghijkl";	//Should be 12 bits. Update as required.
	
    private int counter = 1;
    
    protected byte[] getKey(){
    	//return key.getBytes(StandardCharsets.UTF_8);
    	return key.getBytes();
    }
    
    protected byte[] getNonce(){
    	//return nonce.getBytes(StandardCharsets.UTF_8);
    	return nonce.getBytes();
    }
    
    protected int getCounter(){
    	return this.counter;
    }
    
    protected void setCouter(int counter){
    	if(counter>0)
    		this.counter=counter;
    }
    
    /* ToDo Methods
     * Integrating with Ranger KMS
     * newKey()
     * updateKey()
     * updateNonce()
     */
}
