import java.util.Scanner;

public class Brute{
	public static char[] alphabet;
	public static String pw;

	public static void main(String args[]){
		alphabet = ALPHABET();
        Scanner scanner = new Scanner(System.in);
		System.out.println("Enter your password:");
		pw = scanner.nextLine().trim();

		long start = System.nanoTime();
		String found = bruteForce(pw.length());
		long end = System.nanoTime();
		double elapsed = (end-start)/1000000000.0;
		System.out.println("Password: " + found + ", in: " + elapsed + "s.");
	}

	//with known length
	public static String bruteForce(int length){
		String intial = generateFiller('*', length);
		StringBuilder builder = new StringBuilder(intial);//initial
		String found = push(builder, 0, length);
		return found;
	}

	public static String push(StringBuilder b, int index, int length){
		String result = "";

		for(int i = 0; i < alphabet.length; i++){
			b.setCharAt(index, alphabet[i]);

			if(index == length-1){//if last char
				if (b.toString().equals(pw))
					result = b.toString();
			} else {
				result = push(b, index + 1, length);
			}

			if (!result.equals(""))
				return result;
		}
		return result;
	}

	public static String generateFiller(char toRepeat, int length){
		char[] array = new char[length];
		for(int i = 0; i < length; i++){
			array[i] = toRepeat;
		}
		return new String(array);
	}

	//assumed alphabet in arbitraryorder
	public static char[] ALPHABET(){
		char[] alphabet =
			"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
			.toCharArray();

		return alphabet;
	}
}
