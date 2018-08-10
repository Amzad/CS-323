import java.util.Scanner;
import java.util.Timer;

public class Main {

	public static long time = System.currentTimeMillis();
	public static Timer timer = new Timer();

	public static void print(String x) {
		System.out.println(x);
	}

	public static void printT(String x) {
		System.out.println("[" + (System.currentTimeMillis() - time) + "] : " + x);
	}
	
	public static void printS(String x) {
		System.out.println(timer);
	}

	public static String[] generateData(int complexity) {
		String[] data = { "", ""};
		String full = "ABCD";
		
		if (complexity == 0) {
			String temp = "";
			
			// Create a String with length 20
			for (int i = 0; i < 20; i++) {
				temp = temp + full.charAt((int) (Math.random() * ( full.length() - 0 )));
			}
			
			// Create a pattern with length 2
			String tempPattern = "";
			for (int i = 0; i < 2; i++) {
				tempPattern = tempPattern + full.charAt((int) (Math.random() * ( full.length() - 0 )));
			}

			data[0] = temp;
			data[1] = tempPattern;
			printT("String Created: " + data[0]);
			printT("Pattern Created: " + data[1]);
		}
		else if (complexity == 1) {
			String temp = "";
			
			// Create a String with length 30
			for (int i = 0; i < 30; i++) {
				temp = temp + full.charAt((int) (Math.random() * ( full.length() - 0 )));
			}
			
			// Create a pattern with length 4
			String tempPattern = "";
			for (int i = 0; i < 4; i++) {
				tempPattern = tempPattern + full.charAt((int) (Math.random() * ( full.length() - 0 )));
			}

			data[0] = temp;
			data[1] = tempPattern;
			printT("String Created: " + data[0]);
			printT("Pattern Created: " + data[1]);
		}

		return data;
	}

	public static void main(String[] args) {

		print("Naive vs Advanced Pattern Matching");
		print("By Amzad Chowdhury");
		print("Pick an option below:");
		print("1 - Naive Pattern Matching Algorithm");
		print("2 - KMP Pattern Algorithm");
		print("3 - Rabin-Karp Hashing Algorithm");
		print("4 - All 3 Concurrently with same random input");

		String input = null;
		Scanner in = new Scanner(System.in);
		while (true) {
			try {
				
				input = in.nextLine();
				//print(input);
				if (Integer.parseInt(input) == 1) {
					String[] dapta = generateData(0);

				} else if (Integer.parseInt(input) == 2) {

				} else if (Integer.parseInt(input) == 3) {

				} else if (Integer.parseInt(input) == 4) {

				} else {
					print("Invalid Input. Try again");
				}
			} catch (NumberFormatException e) {
				print("Invalid Input. Try again");
				continue;
			}
		}
	}

	// Code for Naive Pattern Searching
	// Taken from
	// https://www.geeksforgeeks.org/searching-for-patterns-set-1-naive-pattern-searching/

	public static void search(String txt, String pat) {
		int M = pat.length();
		int N = txt.length();

		/* A loop to slide pat one by one */
		for (int i = 0; i <= N - M; i++) {

			int j;

			/*
			 * For current index i, check for pattern match
			 */
			for (j = 0; j < M; j++)
				if (txt.charAt(i + j) != pat.charAt(j))
					break;

			if (j == M) // if pat[0...M-1] = txt[i, i+1, ...i+M-1]
				System.out.println("Pattern found at index " + i);
		}
	}

	// Code for KMP Algorithm
	// Taken from
	// https://www.geeksforgeeks.org/searching-for-patterns-set-2-kmp-algorithm/

	void KMPSearch(String pat, String txt) {
		int M = pat.length();
		int N = txt.length();

		// create lps[] that will hold the longest
		// prefix suffix values for pattern
		int lps[] = new int[M];
		int j = 0; // index for pat[]

		// Preprocess the pattern (calculate lps[]
		// array)
		computeLPSArray(pat, M, lps);

		int i = 0; // index for txt[]
		while (i < N) {
			if (pat.charAt(j) == txt.charAt(i)) {
				j++;
				i++;
			}
			if (j == M) {
				System.out.println("Found pattern " + "at index " + (i - j));
				j = lps[j - 1];
			}

			// mismatch after j matches
			else if (i < N && pat.charAt(j) != txt.charAt(i)) {
				// Do not match lps[0..lps[j-1]] characters,
				// they will match anyway
				if (j != 0)
					j = lps[j - 1];
				else
					i = i + 1;
			}
		}
	}

	void computeLPSArray(String pat, int M, int lps[]) {
		// length of the previous longest prefix suffix
		int len = 0;
		int i = 1;
		lps[0] = 0; // lps[0] is always 0

		// the loop calculates lps[i] for i = 1 to M-1
		while (i < M) {
			if (pat.charAt(i) == pat.charAt(len)) {
				len++;
				lps[i] = len;
				i++;
			} else // (pat[i] != pat[len])
			{
				// This is tricky. Consider the example.
				// AAACAAAA and i = 7. The idea is similar
				// to search step.
				if (len != 0) {
					len = lps[len - 1];

					// Also, note that we do not increment
					// i here
				} else // if (len == 0)
				{
					lps[i] = len;
					i++;
				}
			}
		}
	}

	// Code for Rabin Karp
	// Taken from
	// https://www.geeksforgeeks.org/searching-for-patterns-set-3-rabin-karp-algorithm/

	public final static int d = 256;

	/*
	 * pat -> pattern txt -> text q -> A prime number
	 */
	static void search(String pat, String txt, int q) {
		int M = pat.length();
		int N = txt.length();
		int i, j;
		int p = 0; // hash value for pattern
		int t = 0; // hash value for txt
		int h = 1;

		// The value of h would be "pow(d, M-1)%q"
		for (i = 0; i < M - 1; i++)
			h = (h * d) % q;

		// Calculate the hash value of pattern and first
		// window of text
		for (i = 0; i < M; i++) {
			p = (d * p + pat.charAt(i)) % q;
			t = (d * t + txt.charAt(i)) % q;
		}

		// Slide the pattern over text one by one
		for (i = 0; i <= N - M; i++) {

			// Check the hash values of current window of text
			// and pattern. If the hash values match then only
			// check for characters on by one
			if (p == t) {
				/* Check for characters one by one */
				for (j = 0; j < M; j++) {
					if (txt.charAt(i + j) != pat.charAt(j))
						break;
				}

				// if p == t and pat[0...M-1] = txt[i, i+1, ...i+M-1]
				if (j == M)
					System.out.println("Pattern found at index " + i);
			}

			// Calculate hash value for next window of text: Remove
			// leading digit, add trailing digit
			if (i < N - M) {
				t = (d * (t - txt.charAt(i) * h) + txt.charAt(i + M)) % q;

				// We might get negative value of t, converting it
				// to positive
				if (t < 0)
					t = (t + q);
			}
		}
	}

}
