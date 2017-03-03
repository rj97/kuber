import java.nio.charset.StandardCharsets;

public class Config{
	private String key = "abcdefghijklmnopqrstuvwxyz";
    
    private String nonce = "abcdefghijkl";
	
    private int counter = 1;
    
    protected byte[] getKey(){
    	return key.getBytes(StandardCharsets.UTF_8);
    }
    
    protected byte[] getNonce(){
    	return nonce.getBytes(StandardCharsets.UTF_8);
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
