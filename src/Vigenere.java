import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Vigenere {
	public static String alphabet = "abcdefghijklmnopqrstuvwxyz";
	final static int GUESS_RANGE =15;
	final static double EXPECTED_FREQ = 0.065;
	
	public static void main(String arg[]) throws IOException {	
		String input_text ="";
		List<Integer> best_key_length = new ArrayList<Integer>();
		
		Path filePath = Paths.get("C:/", "Users/Acer/Desktop/", "encrypted_text.txt");
        try
        {
            String content = Files.readString(filePath);
            input_text = content.trim().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
            System.out.println(input_text);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        double [] ic_value_out = new double [GUESS_RANGE];
		
		//Tinh gia tri ic va luu vao array ic_value_out
		Cal.ic_value_Cal(input_text,alphabet,ic_value_out);
		
		//Tim ra cac key phu hop (best_key_length)
	    for(int i=0; i<ic_value_out.length; i++) {
	        double temp_diff = Math.abs(ic_value_out[i]-EXPECTED_FREQ);
	        if(temp_diff <= EXPECTED_FREQ*0.2) {
	            best_key_length.add(i+1);
	        }
	    }
	    for(int i=0; i<best_key_length.size(); i++) {
			System.out.println("Best key length is: "+best_key_length.get(i));
	    }
		//Tim key tuong ung voi best_key_length tim duoc
	    for (int i=0; i<best_key_length.size(); i++) {
	    	String key = Cal.findKey(input_text, best_key_length.get(i), alphabet);
	    	System.out.println("Key is: "+key);
	    	System.out.println(Cal.decrypt(input_text,key));
			//Giai ma bang key tim duoc
	    	PrintWriter writer = new PrintWriter("C:/Users/Acer/Desktop/decrypted_text.txt", "UTF-8");
	    	writer.println(Cal.decrypt(input_text, key));
	    	writer.close();
	    }
	}
}
