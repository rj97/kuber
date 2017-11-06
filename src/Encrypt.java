import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import algorithms.ChaCha20;

public class Encrypt extends Configured implements Tool {
    
    public static final String FS_PARAM_NAME = "fs.defaultFS";
    
    public int run(String[] args) throws Exception {
    	if (args.length < 2) {
            System.err.println("HdfsWriter [local input path] [hdfs output path]");
            return 1;
        }
        
    	// Get the filename out of the file path
	    String filename = args[0].substring(args[0].lastIndexOf('/') + 1,args[0].length());

	    String dest = args[1];
	    // Create the destination path including the filename.
	    if (dest.charAt(dest.length() - 1) != '/') {
	      dest = dest + "/" + filename;
	    } else {
	      dest = dest + filename;
	    }
	    
        String localInputPath = args[0];
        
        File inputPath = new File(localInputPath);
        Path outputPath = new Path(dest);
        
        if(!inputPath.exists()){
        	System.err.println("Input file does not exist!");
            return 1;
        }
        
        Configuration conf = getConf();
        System.out.println("configured filesystem = " + conf.get(FS_PARAM_NAME));
        
        FileSystem fs = FileSystem.get(conf);
        
        if (fs.exists(outputPath)) {
            System.err.println("Output path exists!");
            return 1;
        }
        
        InputStream is = new BufferedInputStream(new FileInputStream(localInputPath));
        OutputStream os = fs.create(outputPath);
        
        System.out.println("\nKuber encryption started!\n");
        
        byte[] b = new byte[1024*100];
        
	    int numBytes = 0;
	    
	    while ((numBytes = is.read(b)) > 0) {
	      byte[] block = Arrays.copyOfRange(b, 0, numBytes);
	      byte[] encrypted = encrypt(block);
	      os.write(encrypted, 0, numBytes);
	    }
	    
	    is.close();
	    os.close();
	    
        return 0;
    }
    
    protected byte[] encrypt(byte[] plain) {
        Config conf = new Config();
        
        byte[] encrypted = new byte[plain.length];
        
        try {
            ChaCha20 cipher = new ChaCha20(conf.getKey(), conf.getNonce(), conf.getCounter());
            cipher.encrypt(encrypted, plain, plain.length);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        
        return encrypted;
    }
    
    public static void main( String[] args ) throws Exception {
        int returnCode = ToolRunner.run(new Encrypt(), args);
        System.exit(returnCode);
    }
}