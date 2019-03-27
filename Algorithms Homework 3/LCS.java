package lcs;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LCS {

	/**
	 * memoCheck is used to verify the state of your tabulation after performing
	 * bottom-up and top-down DP. Make sure to set it after calling either one of
	 * topDownLCS or bottomUpLCS to pass the tests!
	 */
	public static int[][] memoCheck;

	// -----------------------------------------------
	// Shared Helper Methods
	// -----------------------------------------------

	// [!] TODO: Add your shared helper methods here!
	
	// TODO: FINISH!
	private Set<String> collectSolution(String rStr, int r, String cStr, int c, int[][] memo) {
		
		Set<String> result = new HashSet<String>();
		
		// Base Case: If gutter reached
		if (r == 0 || c == 0) {
			result.add("");
			return result;
		}
		// Recursive Case 1: Matched Letters
		if (rStr.charAt(r) == cStr.charAt(c)) {
			result = collectSolution(rStr, r-1, cStr, c-1, memo);
			char matchedLetter = rStr.charAt(r);
			addLetter(matchedLetter, result);
			return result;
		}
		// Recursive Case 2: Mismatched Letters --> Cell to left >= cell above
		if (memo[r][c-1] >= memo[r-1][c]) {
			result.addAll(collectSolution(rStr, r, cStr, c-1, memo));
			
		}
		// Recursive Case 3: Mismatched Letters --> Cell above >= cell to left
		if (memo[r-1][c] >= memo[r][c-1]) {
			result.addAll(collectSolution(rStr, r, cStr, c-1, memo));
		}
		return result;
	}

	// decides if to use BU or TD
	private void executeLCS(boolean topDown, int[][] table) {
		// if true --> top down
		// else --> bottom up
		throw new UnsupportedOperationException();
		// fill the table either bottom-up or top-down
		// use answer retrieval helper -- collectSolution
	}
	
	// [!] TODO: getSolution helper and call it in executeLCS, addLetter helper
	
	private Set<String> addLetter(char letter, Set<String> currentResults) {
		Set<String> withAddedLetter = new HashSet<String>();
		for (String s : currentResults) {
			withAddedLetter.add(s + Character.toString(letter));
		}
		return withAddedLetter;
	}

	// -----------------------------------------------
	// Bottom-Up LCS
	// -----------------------------------------------

	/**
	 * Bottom-up dynamic programming approach to the LCS problem, which solves
	 * larger and larger subproblems iterative using a tabular memoization
	 * structure.
	 * 
	 * @param rStr The String found along the table's rows
	 * @param cStr The String found along the table's cols
	 * @return The longest common subsequence between rStr and cStr + [Side Effect]
	 *         sets memoCheck to refer to table
	 */
	public static Set<String> bottomUpLCS(String rStr, String cStr) {
		// executeLCS();	
		throw new UnsupportedOperationException();
	}

	// [!] TODO: Add any bottom-up specific helpers here!

	// populates memoization table [Done for now]
	private static int[][] bottomUpFillTable(String rStr, String cStr) {

		int[][] table = new int[rStr.length()][cStr.length()];

		for (int row = 0; row < rStr.length(); row++) {
			for (int col = 0; col < cStr.length(); col++) {
				// Case 1: Mismatched letters
				if (rStr.charAt(row) != cStr.charAt(col)) {
					table[row][col] = Math.max((table[row - 1][col]), (table[row][col - 1]));
				} else {
					// Case 2: Matched letters
					table[row][col] = 1 + table[row - 1][col - 1];
				}
			}
		}
		memoCheck = table;
		return table;
	}

	// -----------------------------------------------
	// Top-Down LCS
	// -----------------------------------------------

	/**
	 * Top-down dynamic programming approach to the LCS problem, which solves
	 * smaller and smaller subproblems recursively using a tabular memoization
	 * structure.
	 * 
	 * @param rStr The String found along the table's rows
	 * @param cStr The String found along the table's cols
	 * @return The longest common subsequence between rStr and cStr + [Side Effect]
	 *         sets memoCheck to refer to table
	 */
	public static Set<String> topDownLCS(String rStr, String cStr) {
		// one line call executeLCS
		throw new UnsupportedOperationException();
	}

	// [!] TODO: Add any top-down specific helpers here!

	// Populates memoization table
	private int[][] topDownFillTable(String rStr, int r, String cStr, int c, int[][] table) {
		// memoize this with 2D boolean array
		table = new int[rStr.length()][cStr.length()];
		throw new UnsupportedOperationException();
	}

}
