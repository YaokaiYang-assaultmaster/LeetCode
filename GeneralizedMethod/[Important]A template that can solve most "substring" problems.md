The methods being used to solve substring problems is pretty much of the same structure.
For example, 76. Minimum Window Substring,  Longest Substring with At Most Two Distinct Characters, Longest Substring Without Repeating Characters and so on
For most substring problem, we are given a string and need to find a substring of it which satisfy some restrictions. 
A general way is to use a hashmap assisted with two pointers. The templete is given below. 

``` Java
public Solution {
	public int findSubstring(string s) {
		int[] map = new int[128];
		//Use a int array as a hashmap since every character has its unique ASCII value.
		//By this way we can save some space and time cost
		//Or we could use HashMap 
		//Map<E, E> map = new HashMap<E, E>() 

		int counter; // check wether the subtring is valid
		int begin = 0, end = 0; // two pointers, one point to head and one to tail
		int len; //the length of the substring

		char[] chS = s.toCharArray();
		for {
			/* initilize the hash map here */
		}

		while (end < s.length()) {

			if (map[chS[end++]]-- ?) {
				/* modify  counter here */
			}

			while (/* counter condition */) {
				/* update here if find minimum */

				//increase begin to make it invalid/valid again

				if (map[chS[begin++]]++ ?) {
					/* modify counter here */
				}
			}

			/* update d here if find maximum */
		}

		return d // Also be cautious about return conditions. There may be some corner case
	}
}
```

One thing needs to mention is that when asked to find maximum substring, we should update maximum after the inner while loop to guarantee that the substring is vadid. On the other hand, when asked to find minimum substring, we should update minimum inside the inner while loop. 

Refer to code of Minimum Window Substring,  Longest Substring with At Most Two Distinct Characters, Longest Substring Without Repeating Characters to see this. 
