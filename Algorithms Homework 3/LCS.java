// Jigar Swaminarayan
package lcs;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LCS {

	/**
	 * memoCheck is used to verify the state of your tabulation after performing
	 * bottom-up and top-down DP. Make sure to set it after calling either one of
	 * topDownLCS or bottomUpLCS to pass the tests!2
	 */
	public static int[][] memoCheck;

	// -----------------------------------------------
	// Shared Helper Methods
	// -----------------------------------------------
	
	private static Set<String> collectSolution(String rStr, int r, String cStr, int c, int[][] memo) {
		// Base Case: If gutter reached
		if (r == 0 || c == 0) {
			Set<String> result = new HashSet<String>();
			result.add("");
			return result;
		}
		// Recursive Case 1: Matched Letters
		if (rStr.charAt(r-1) == cStr.charAt(c-1)) {
			char matchedLetter = rStr.charAt(r-1);
			return addLetter(matchedLetter, collectSolution(rStr, r-1, cStr, c-1, memo));
		}
		// Recursive Case 2: Mismatched Letters --> Cell to left >= cell above
		Set<String> result = new HashSet<String>();
		if (memo[r][c-1] >= memo[r-1][c]) {
			result.addAll(collectSolution(rStr, r, cStr, c-1, memo));
			
		}
		// Recursive Case 3: Mismatched Letters --> Cell above >= cell to left
		if (memo[r-1][c] >= memo[r][c-1]) {
			result.addAll(collectSolution(rStr, r - 1, cStr, c, memo));
		}
		return result;
	}

	// [!] TODO: Make sure memoCheck is being used properly
	private static Set<String> executeLCS(boolean topDown, String rStr, String cStr) {
		Set<String> solution = new HashSet<String>();
		int rLen = rStr.length();
		int cLen = cStr.length();
		int[][] memo = new int[rLen+1][cLen+1];
		if (topDown) {
			boolean[][] graveyard = new boolean[rLen+1][cLen+1];
			topDownFillTable(rStr, rLen, cStr, cLen, memo, graveyard);
			solution = collectSolution(rStr, rLen, cStr, cLen, memo);
		} else {
			memo = bottomUpFillTable(rStr, cStr);
			solution = collectSolution(rStr, rLen, cStr, cLen, memo);
		}
		memoCheck = memo;
		return solution;
	}
	
	private static Set<String> addLetter(char letter, Set<String> currentResults) {
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
		return executeLCS(false, rStr, cStr);
	}

	private static int[][] bottomUpFillTable(String rStr, String cStr) {
		int[][] table = new int[rStr.length()+1][cStr.length()+1];
		
		for(int row = 1; row < rStr.length() + 1; row++) {
			for(int col = 1; col < cStr.length() + 1; col++) {
				if (rStr.charAt(row-1) == cStr.charAt(col-1)) {
					table[row][col] = 1 + table[row-1][col-1];
				} else {
					table[row][col] = Math.max(table[row-1][col], table[row][col-1]);
				}
			}
		}
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
		return executeLCS(true, rStr, cStr);
	}

	private static int topDownFillTable(String rStr, int r, String cStr, int c, int[][] memo, boolean[][] graveyard) {
		
		if (r == 0 || c == 0) {
			return 0;
		}
		if (graveyard[r][c] == true) {
			return memo[r][c];
		} else if (rStr.charAt(r-1) == cStr.charAt(c-1)) {
			graveyard[r][c] = true;
			memo[r][c] = 1 + topDownFillTable(rStr, r-1, cStr, c-1, memo, graveyard);
			return memo[r][c];
		} else {
			memo[r][c] = Math.max(topDownFillTable(rStr, r-1, cStr, c, memo ,graveyard), 
					topDownFillTable(rStr, r, cStr, c-1, memo, graveyard));
			graveyard[r][c] = true;
			return memo[r][c];
		}
	}
}
