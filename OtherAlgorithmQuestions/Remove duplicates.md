> Given an integer array which has the property that all the elements haveing same value are adjacent to each other. 
> e.g. `{2,2,2,1,5,6,6,7,7}`, output: Remove duplicate element in place and output the length of the new array. Do it __inplace__ and we don't care about the elements outside the new length of the array. 
> e.g. return 5 and the input arrya will become `{2,1,5,6,7,x,x,x,x}`. "x" means we don't care about its value. 

- Analysis:

  A brute force method for this question would be to traverse the array and store existed numbers into a HashSet on the fly. Each time we encountering a new number, put it to the last position of the new array. 
	However, in order to solve this question in place, we must not store all prvious existed numbers. Notice that in the requirement mentioned that all the duplicates are adjacent to each other. Thus we only have to keep one variable for the previous number. Each time we encounter at a number, set the previous number variable to this new one and swap the number with the last position of the new array. By this way we can do this in place. 
	
	The Time complexity of this algorithm is `O(n)` and the space complexity is `O(1)`. Because we only traverse the whole array once and only use one variable. 
	
	
The Java implementation is showed as following:

```
public class Solution {
	public int removeDuplicates(int[] array) {
		if (array == null || array.length == 0) return 0;

		int prev = array[0];
		int last = 1;

		for (int i = 1; i < array.length; i++) {
			if (array[i] != prev) {
				prev = array[i];
				swap(array, last, i);
				last++;
			}
		}

		return last;
	}

	private void swap(int[] array, int i1, int i2) {
		int tmp = array[i1];
		array[i1] = array[i2];
		array[i2] = tmp;
	}
}
```
