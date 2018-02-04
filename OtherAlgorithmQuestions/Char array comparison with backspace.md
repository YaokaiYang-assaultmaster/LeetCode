> Given two char arrays, some of their characters are _backspaces_. Write a function to output a boolean value representing the comparison result of the two char arrays after dealing with all the backspaces. 
> e.g.   
> For input `['a', 'b', '\b','b', 'c']` and `['a', 'b', 'c', '\b', 'c']`, the result should be `true`.  
> For input `['\b', '\b', 'a', 'b', '\b','b', 'c']` and `['a', 'b', 'c', '\b', 'c']`, the result should be `true`. Note that when backspaces are at the very beginning fo the array, it has no effect on the characters after it.  
> For input `['a','b','d','\b']` and `['a', 'd']`, the result should be `false`.  
> Solve this question within `O(n)` time, `O(1)` space.  

The idea is simple. We first processing the two input char array in place to remove all backspaces and characters before it. This can be dong via two pointers. During this process we put all remaining characters to the beginning of the two arrays and return the last index.  
Then we first compare the end index. If they are different, then the two array are not the same for sure. Hence we return false.  

Afterwards we compare the subarray from start to the end index of the two input array.

Time complexity: `O(n)` where `n` is the sum of the `arr1.length` and `arr2.length`.  

Space complexity: `O(1)`.  

```Java
public class CharArrayComparison {
	public boolean charArrayComparison(char[] arr1, char[] arr2) {
		int index1 = processBackSpace(arr1);
		int index2 = processBackSpace(arr2);
		
		if (index1 != index2) return false;
		for (int i = 0; i <= index1; i++) {
			if (arr1[i] != arr2[i]) return false;
		}
		return true;
	}
	
	private int processBackSpace(char[] arr) {
		int start = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == '\b') {
				start = Math.max(0, start - 1);
			} else {
				arr[start++] = arr[i];
			}
		}
		
		return start - 1;
	}
}
```
