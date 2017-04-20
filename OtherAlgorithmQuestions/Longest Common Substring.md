> Given 2 Strings s1 and s2, find the longest common substring of s1 and s2. 

- Analysis:

	A brute-force method is easy to think. Simple traverse s1 and find the substring in s2. Suppose the length of s1 and s2 is m and n respectively. Thus there are `O(m ^ 2)` substrings of s1 in total. And compare each substring with s2 is `O(n)`. Hence the total time of the brute-force algorithm is `O(n * m ^ 2)`. 
	
	Can we do better? Yes, the ides is to find the longest common suffix of substrings from both a and b and store them in a table. The optimal substructure is showed as following:
	```
	LCsuffix(s1, s2, i, j) = LCsuffix(s1, s2, i - 1, j - 1) + 1 if s1.charAt(i) == s2.charAt(j)
	LCsuffix(s1, s2, i, j) = 0 else
	```
	In this way we can get the longest common substring in `O(m * n)` time since the size of the table is O(m * n) and we have to traverse this table only once. The space complexity of this algorithm is `O(m * n)` as well because of the 2d array. 
	
***

Java implementation is showed as following:
	
```
public class Solution {
	public int lcs(String s1, String s2) {
		if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) return 0;

		int m = s1.length();
		int n = s2.length();
		int[][] lcs = new int[m + 1][n + 1];
		int max = Integer.MIN_VALUE;
		int index1 = 0;
		int index2 = 0;

		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
					lcs[i][j] = lcs[i - 1][j - 1] + 1;
					if (max < lcs[i][j]) {
						max = lcs[i][j];
						index1 = i;
						index2 = j;
					}
				} else {
					lcs[i][j] = 0;
				}
			}
		}

		String longestSubstring = s1.substring(index1 - max, index1);
		System.out.println(longestSubstring);
		return max;
	}
}
```
