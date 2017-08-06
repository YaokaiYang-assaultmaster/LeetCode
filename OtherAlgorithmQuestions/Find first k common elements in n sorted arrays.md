> Given n sorted arrays, find first k common elements in them. 
> E.g. the common elements of {{1,3,5,7}, {3,4,5,6}, {3,5,6,8,9}, {2,3,4,5,11,18}} are 3 and 5. 

- Analysis:
In order to solve this question, we need to consider 2 aspects, which is common elements and sorted arrays. In this sense, a common elements 
of n sorted arrays need to appear exactly n times in the input. And sorted means we can perform special operation like binary search on the
arrays.Thus there are roughly 2 ideas for it, showed as following:
  1. Choose a pivot array, for each element of it, perform binary search in all other arrays. And keep a counter for each pivot element. A counter
    of n indicates this element is a common element. Suppose the average length of each array is n as well. For each element in the pivot array, we perform (n - 1) binary
    searches in the remaining arrays. Each binary search takes `O(logn)` time. In total, the time complexity is `O(n * (n - 1) * logn) = O(n^2logn)`. 
  2. However, we did some duplicate works in the above algorithm. That is, in the binary search step, we compare the elements in the pivot array
    again and again with all the elements from `0` to `n - 1`. In order to optimize, we could keep indexes of the farmost position we have traversed 
    for each array. Because all the arrays are sorted or monotonically non-decreasing, so positions we have already checked is useless for us. Thus the algorithm could be described as following:
		1. Choose a pivot array, in which each element is going to be used for comparing. 
		2. Keep an array of length n - 1, in which stores the pointer indexes of each remaining arrays. 
		3. For an element of pivot array, compare it with elements from each remaining arrays from the corresponding pointer index.
		  - if pivot element is greater than current number, increase the index.
		  - if pivot element is equal to current number, increase the counter.
		  - if pivot element is less than current number, no match is found. break.
		4. if the counter reaches n, a common element is found. 
    
    This algorithm takes `O(n * n)` time and `O(n)` space. Since we have to traverse each number in the 2d array and need to keep a pointer for 
    each row in it. However, the `O(n)` space can be saved by using the pivot array to store the pointer indexes. 

    __For the seek of simplicity, we use linear comparision in the second algorithm. However, use binary search in each comparison step from pointer index to the end of the array
      could further increase efficiency. We can further slightly improve the efficiency by choose the array with the greatest first number as the pivot
      array.__

A Java implementation of the second algorithm is showed as following:
```
public class Solution {
	public int[] firstKCommonElements(int[][] arrays, int k) {
		if (arrays == null || arrays.length == 0 || arrays[0] == null || arrays[0].length == 0) {
			return new int[]{};
		}

		int n = arrays.length;
		int[] pointers = new int[n];
		int[] ret = new int[k];
		int index = 0;

		for (int i = 0; i < arrays[0].length; i++) {
			int pivot = arrays[0][i];
			int counter = 1;
			for (int j = 1; j < n; j++) {
				while (pointers[j] < arrays[j].length && pivot > arrays[j][pointers[j]]) {
					pointers[j]++;
				}
				if (pointers[j] == arrays[j].length || pivot != arrays[j][pointers[j]]) {
					break;
				}
				counter++;
			}
			if (counter == n) {
				ret[index++] = pivot;
			}
			if (index == k) {
				return ret;
			}
		}

		return ret;
	}
}
```
 
