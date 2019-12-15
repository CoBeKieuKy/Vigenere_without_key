import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Cal {
	//Bang tan suat cac chu cai trong tieng Anh
	final static double freq[] = {
		    0.08167, 0.01492, 0.02782, 0.04253, 0.12702, 0.02228, 0.02015,
		    0.06094, 0.06966, 0.00153, 0.00772, 0.04025, 0.02406, 0.06749,
		    0.07507, 0.01929, 0.00095, 0.05987, 0.06327, 0.09056, 0.02758,
		    0.00978, 0.02360, 0.00150, 0.01974, 0.00074
		};
	
	//Sap xep ket qua
	public static LinkedHashMap<Integer, Double> sortHashMapByValues(Map<Integer, Double> passedMap) {
	    List<Integer> mapKeys = new ArrayList<>(passedMap.keySet());
	    List<Double> mapValues = new ArrayList<>(passedMap.values());
	    Collections.sort(mapValues);
	    Collections.sort(mapKeys);

	    LinkedHashMap<Integer, Double> sortedMap =
	        new LinkedHashMap<>();

	    Iterator<Double> valueIt = mapValues.iterator();
	    while (valueIt.hasNext()) {
	        Double val = valueIt.next();
	        Iterator<Integer> keyIt = mapKeys.iterator();

	        while (keyIt.hasNext()) {
	            Integer key = keyIt.next();
	            Double comp1 = passedMap.get(key);
	            Double comp2 = val;

	            if (comp1.equals(comp2)) {
	                keyIt.remove();
	                sortedMap.put(key, val);
	                break;
	            }
	        }
	    }
	    return sortedMap;
	}
	
	public static int getMinValue(double[] numbers){
		  double minValue = numbers[0];
		  int index = 0;
		  for(int i=1;i<numbers.length;i++){
		    if(numbers[i] < minValue){
			  minValue = numbers[i];
			  index = i;
			}
		  }
		  return index;
	}
	
	//Tinh gia tri ic_value trong cac truong hop
	public static void ic_value_Cal (String input_text, String alphabet, double[] ic_value_out) {
		int i,j,k,m;
		float sum;
		double average=0;
		
		for ( i=1; i<=Vigenere.GUESS_RANGE; i++) {
			System.out.printf("\nFinding ic_value number %d:\n",i);
			average =0;
			for (j=0; j<i; j++) {
				sum =0;
				float length_of_each=0;
				int[] freq_of_each = new int[26];
				double[] ic_value = new double[i];
				
				for( k=j; k<input_text.length(); k+=i) {
					for(m=0; m<alphabet.length(); m++) {
						if(Character.compare(input_text.charAt(k),alphabet.charAt(m))==0) {
							freq_of_each[m]++;
							break;
						}
					}
					length_of_each++;
				}
				for( k=0; k<alphabet.length(); k++) {
					sum += freq_of_each[k]*(freq_of_each[k]-1);
				}
				ic_value[j] = (sum / (length_of_each*(length_of_each-1)));
//				System.out.println("ic_value :  "+ic_value[j]);
				average = average + ic_value[j]/i;
//				System.out.println();
				}
			ic_value_out[i-1] = average;
			System.out.printf("The Average value of ic_value number %d: %f\n",i,average);
			System.out.println();			
		}
	}
	
	//Phuong thuc tim gia tri cu the cua key
	public static String findKey(String input_text, int key_length, String alphabet) {
		StringBuilder key= new StringBuilder("");
		int k,m,j,i,n;
		StringBuilder sub_input_text= new StringBuilder("");
		
		for( j=0; j<key_length; j++) {
			double [] out = new double[26];
			System.out.println();
			System.out.println("********************CHECKING SUB_ENCRYPTED_TEXT #"+(j+1)+"********************");
			System.out.println();
			for( k=j; k<input_text.length(); k+=key_length) {
				sub_input_text.append(input_text.charAt(k));
			}	//cat input text thanh tung phan
			System.out.println("Sub encrypted text #"+(j+1)+ ": "+sub_input_text);
			System.out.println();
			for( i=0; i<26; i++) {//voi moi phan thi tien hanh shift tu 1->25
				double[] freq_of_each = new double[26];
				double [] out_X = new double[26];
				for( k=0; k<sub_input_text.length(); k++) {
					for(m=0; m<alphabet.length(); m++) {
						if(Character.compare((char)((sub_input_text.charAt(k) - 'a'+i)%26+'a'), alphabet.charAt(m))==0) {
							freq_of_each[m]++;
							break;
						}
					}
				}
//				System.out.println();
				for ( n=0; n<26; n++) {
					out_X[i] += Math.pow(((float)freq_of_each[n]/sub_input_text.length()-freq[n]),2)/freq[n];
				}
				out[i] = out_X[i];
//				System.out.println("Do dich chuyen la: "+i);
//				System.out.println(out_X[i]);
//				System.out.println("---------------------------------------------------------");
			}
			System.out.println("Do lech so voi van ban goc: "+(getMinValue(out)));
			key.append(alphabet.charAt((26-getMinValue(out))%26));
			sub_input_text.setLength(0);
		}
		return key.toString();
	}
	
	//Phuong thuc giai ma decrypt nhan vao doan ma hoa va key
		public static String decrypt(String encrypted_text, String key) {
			String decrypted_text = "";
		    for (int i = 0, j = 0; i < encrypted_text.length(); i++) {
		    	char c = encrypted_text.charAt(i);
		        if (c < 'a' || c > 'z') continue;
		        decrypted_text += (char)((c - key.charAt(j) + 26) % 26 + 'a');
	            j = ++j % key.length();
		    }
		    return decrypted_text;
		}
}

