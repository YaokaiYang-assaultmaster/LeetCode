> Given a String with parentheses, return a string with balanced parentheses by removing fewest characters possible. You cannot add anything to the string.   
> E.g. <br>
> balabce("()") -> "()"  
> balance(")(") -> ""  
> balance("(((((()") -> "()"  
> balance("()(((()()") -> "()()()"  

- Analysis:  
	Obviously the requirement of this question is to delete all unclosed parentheses and reserve closed parentheses. At the same time, we need to close a right parenthesis with its nearest left parenthesis.  
	The idea is like following:  
	1. Traverse the string from left to right. Count the number of left and right parenthesis. If the number of right parenthesis is greater than the number of left parenthesis, delete the right parenthesis.  
	2. Traverse the string from right to left. Count the number of left and right parenthesis. If the number of left parenthesis is greater than the number of right parenthesis, delete the left parenthesis. 

	`O(2n) = O(n)` time, `O(1)` space. n is the length of the string. 

Java implementation:   
```
public class Solution {
	public String balance(String s) {
		int l = s.length();

		char[] chs = s.toCharArray();

		int L = 0;
		int R = 0;

		for (int i = 0; i < l; i++) {
			if (chs[i] == '(') L++;
			if (chs[i] == ')') R++;

			if (R > L) {
				chs[i] = '*';
				R--;
			}
		}
		
		L = 0;
		R = 0;

		for (int i = l - 1; i >= 0; i--) {
			if (chs[i] == ')') R++;
			if (chs[i] == '(') L++;

			if (L > R) {
				chs[i] = '*';
				L--;
			}
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < l; i++) {
			if (chs[i] != '*') {
				sb.append(String.valueOf(chs[i]));
			}
		}

		return sb.toString();
	}
}
```
