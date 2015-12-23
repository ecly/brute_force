import java.io.Console;
import java.util.Arrays;

public class Brute{
	public static char[] alphabet;
	public static char[] pw;

	public static void main(String args[]){
		alphabet = ALPHABET();
        Console console = System.console();
		char[] pw = console.readPassword("Enter your secret password: ");

		long start = System.nanoTime();
		String found = bruteForce(pw.length);
		long end = System.nanoTime();
		double elapsed = (end-start)/1000000000.0;
		System.out.println("Password: " + found + ", in: " + elapsed + "s.");
	}

	//with known length
	public static String bruteForce(int length){
		String found = push(new char[length], 0, length);
		return found;
	}

	public static String push(char[] b, int index, int length){
		String result = "";

		for(int i = 0; i < alphabet.length; i++){
			b[index] = alphabet[i];

			if(index == length-1){//if last char
				if (Arrays.equals(b, pw))
					result = new String(b);
			} else {
				result = push(b, index + 1, length);
			}

			if (!result.equals(""))
				return result;
		}
		return result;
	}

	//assumed alphabet in arbitraryorder
	public static char[] ALPHABET(){
		char[] alphabet =
			"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
			.toCharArray();

		return alphabet;
	}
}
