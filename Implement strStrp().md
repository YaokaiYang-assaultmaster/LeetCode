> Implement a function strStrp(String a, String b), which returns index where any permutation of b is a substring of a. 
> E.g. strStrp("hello", "ell"), return 1; strStrp("hello", "lle"), return 1; strStrp("hello", "wor"), return -1.



- Analysis:
First, let us assume there is only lowever case characters in a and b. No upper cases, no numbers.<br>
This question asks us to implement a function to find the index of the start point of the substring where a permutation of b is in this substring
of a. Unlike strStr(), which we could solve by comparing each character in the substring of a with b, or by KMP algorithm, we need to consider
permutation here. Thus we cannot compare substring of a with b directly. Considering we only need the index, it would be a waste of space and time
to generate every permutation of b and store them in a hashset, then compare a's substrings with elements in this hashset. <br>
Remember we could always decide if 2 strings are permutation of each other by comparing the count of each characters in them, hence solve our
energy by avoiding generating permutations. This idea has several different implementations, concluded as following:
  1. The most elegent and complex one, used in [76. Minimum Window Substring](https://github.com/YaokaiYang-assaultmaster/LeetCode/blob/master/76.%20Minimum%20Window%20Substring).
     This method uses the sliding window concept, which is summarized [here](https://github.com/YaokaiYang-assaultmaster/LeetCode/blob/master/%5BImportant%5DA%20template%20that%20can%20solve%20most%20%22substring%22%20problems.md).
     In this method, we use a HashMap to record the appearance number of each character in the needle. And during traversing haystack, we change the 
     appearance count of each character (adding or substracting 1). In addition, we also keep a counter to keep track of the total match time, aiming at finding out match in `O(1)` time.
     Once the counter reaches 0, a match is found. This method could be used to not only __detect permutations__, but also to __find discontinuous substrings with given characters contained in it__. 
     This method requires O(n) time and O(m) space to detect any match. n is the length of haystack, m is the length of needle. However, use this method here 
     is kinda underemployed and overcomplexed. 
  2. String concatenation. This method uses the idea of counting the number of occurance of character a to z in a given string. Thus all permutations
     of a given string has the same concatenation form. In this way, they can be compared using hash within O(1) time. E.g. "abcb" = "a1b2c1".
     However, in this question, since we are deleting 1 character out and adding one character in each time during the traversing process, we could 
     initialize a concatenation "a0b0....y0z0" at the beginning and change one counte each time. This could save us a lot of time used for constructing the 
     concatenation. 
  3. Multiplication of prime numbers. This method give a prime number to each of the 26 characters. Based on the fact that multiplications of those primes are not gonna be the same
     unless the characters are the same, we could use this product as a key to compare if 2 strings contains the same characters. This idea is used in 
     [49. Group Anagrams](https://github.com/YaokaiYang-assaultmaster/LeetCode/blob/master/49.%20Group%20Anagrams).

- Based on above analysis, we have several different ways to solve this question. Method 2 is implemented in Java as following. 
  This algorithm takes O(n) time and O(constant) = O(1) space. n is the length of haystack, which is, a. The reason is that String a is traversed only once and set.contains() takes O(1) time each time. 

``` Java
public class Solution {
	public int strStrp(String a, String b) {
		if (b == null || b.length() == 0) {
			return 0;
		}

		StringBuilder sb = new StringBuilder();
		StringBuilder needle = new StringBuilder();
		for (char c = 'a'; c <= 'z'; c++) {
			sb.append(c);
			sb.append(0);
			needle.append(c);
			needle.append(0);
		}

		
		for (int i = 0; i < b.length(); i++) {
			int index = b.charAt(i) - 'a';
			int count = needle.charAt(index * 2 + 1) - '0';
			count++;
			needle.replace(index * 2 + 1, index * 2 + 2, String.valueOf(count));
		}

		Set<String> set = new HashSet<>();
		set.add(needle.toString());

		int i = 0, j = 0;
		while (i < a.length() - b.length()) {
			while (j - i < b.length()) {
				int index = a.charAt(j) - 'a';
				int count = sb.charAt(index * 2 + 1) - '0' + 1;
				sb.replace(index * 2 + 1, index * 2 + 2, String.valueOf(count));
				j++;
			}

			if (j - i == b.length()) {
				if (set.contains(sb.toString())) {
					return i;
				}
				
				int index = a.charAt(i) - 'a';
				int count = sb.charAt(index * 2 + 1) - '0' - 1;
				sb.replace(index * 2 + 1, index * 2 + 2, String.valueOf(count));
				i++;
			}

		}

		return -1;
	}
}
```
