> Given an input int array with positive, negative numbers and 0s. Putting negative number on the left side, zero in the middle and positive on the right side. Do it in `O(n)` time and `O(1)` space. 

- Analysis:  
This question is very similar to _Sorting Colors_. We just need to keep 2 pointers for the last position of negative numbers and first place of positive numbers and put numbers to its corresponding position during traversal. Zeros will be in the middle automatically. 

Java implementation is showed as following:

```
public class Solution {
	public void movingNumber(int[] nums) {
		if (nums == null || nums.length == 0) {
			return;
		}

		int start = 0;
		int end = nums.length - 1;

		for (int i = 0; i <= end; i++) {
			if (nums[i] < 0) {
				swap(nums, i, start);
				start++;
			}
			if (nums[i] > 0) {
				swap(nums, i, end);
				end--;
				i--;
			}
		}
	}

	private void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}
}
```
