import java.io.Console;
import java.util.Arrays;
import java.util.concurrent.*;

//alphabet ordering based on data from 2009
//http://reusablesec.blogspot.dk/2009/05/character-frequency-analysis-info.html
public class Brute{
	private static char[] alphabet;
	private static char[] firstCharAlphabet;
	private static char[] pw;
	private static long start;

	public static void main(String args[]){
		firstCharAlphabet = FIRSTCHARALPHABET();
		alphabet = ALPHABET();
        Console console = System.console();
		pw = console.readPassword("Enter your secret password: ");

		start = System.nanoTime();
		concurrentBruteForce(pw.length);
	}

	//with known length
	public static void bruteForce(int length){
		String found = firstPush(new char[length], 0, length, alphabet.length);
		if (!found.equals("")) found(found);
		else System.out.println("Failed!");
	}

	public static String push(char[] b, int index, int length){
		String result = "";

		for(int i = 0; i < alphabet.length; i++){
			b[index] = alphabet[i];

			if(index == length-1){//if last char
				if (Arrays.equals(b, pw)){
					System.out.println(new String(b));
					result = new String(b);
				}
			} else {
				result = push(b, index + 1, length);
			}

			if (!result.equals("")){
				return result;
			}
		}
		return result;
	}

	//spreads the threads based on the first char
	public static void concurrentBruteForce(int length){
		int amountOfCores = Runtime.getRuntime().availableProcessors();
		ExecutorService e = Executors.newFixedThreadPool(amountOfCores);
		for(int i = 0; i < amountOfCores; i++){
			final int start = i * amountOfCores;
			final int end;
			final int coreNumber = i;
			if((i+1) * amountOfCores <= alphabet.length) end = (i+1) * amountOfCores;
			else end = alphabet.length;

			//thread for this subarray
			Thread t = new Thread(new Runnable() {
	     		public void run() {
	        		String found = firstPush(new char[length], start, length, end);
					if(!found.equals("")) {
						found(found);
						e.shutdownNow();
					}
	     		}
			});
			e.execute(t);
		}
	}

	//outer is used to spread the threads and uses optimized alphabet
	public static String firstPush(char[] b, int start, int length, int end){
		String result = "";

		for(int i = start; i < end; i++){
			b[0] = firstCharAlphabet[i];

			result = push(b, 1, length);
			if (!result.equals("")) return result;
		}
		return result;
	}

	public static void found(String found){
		long end = System.nanoTime();
		double elapsed = (end-start)/1000000000.0;
		System.out.println("Found password: " + found + ", in: " + elapsed + "s.");
	}

	//assumed alphabet missing various symbols,
	//sorted in order of common frequency as
	public static char[] ALPHABET(){
		char[] alphabet =
			"aeorisn1tl2md0cp3hbuk45g9687yfwjvzxqASERBTMLNPOIDCHGKFJUWYVZQX"
			.toCharArray();

		return alphabet;
	}

	//assumed alphabet missing various symbols
	//
	public static char[] FIRSTCHARALPHABET(){
		char[] alphabet =
			"s1mpabctdrlfhgkjnw2ei0ov3q45796z8yuxSMPBACDTJRLFGHKNEWVIOZUQYX"
			.toCharArray();

		return alphabet;
	}
}
