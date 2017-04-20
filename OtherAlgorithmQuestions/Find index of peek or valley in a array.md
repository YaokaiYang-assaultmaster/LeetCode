> Given an input array in which `A[i] = A[i - 1] -1/+1`. And there is only one peek or one valley in the array. Find out the index of the peek or the valley. 

- Analysis:

  The `O(n)` solution is rather easy to think of. Just traverse the whole array and find out the index of the peek or valley. 

  A more efficient solution would be to use __Binary Search__. Observing that each number is different from its previous one with only 1, we could utilize this in the binary search. The ides is like following:
    1. If `nums[mid] - nums[mid - 1]` has a different symbol with `nums[mid + 1] - nums[mid]`, then `mid` is the index we want. 
    2. If `Math.abs(nums[mid] - nums[start]) == mid - start`, that means subarray from start to mid is monotonical, peek or valley is in subarray from mid to end.
    3. Other wise peek or valley is in subarray from start to mid. 

  However, after a careful thinking, this question is solvable in `O(1)` time. Still, considering that each number is different from its previous one with only 1, we could get following conditions for the question. 
    1. If there is a peek exists: `index = (nums.length - 1 + nums[end] - nums[start]) / 2`
    2. If there is a valley exists: `index = (nums.length - 1 + nums[start] - nums[end]) / 2`

  In order to know if there is a peek or a valley, we could use nums[1] - nums[0] to see the initial slope. 

  Thus we can solve it in O(1) time. 

Java implementation of __Binary Search__ is showed as following:

```
public class Solution {
	public int find(int[] nums) {
		if (nums == null || nums.length == 0) return -1;

		int len = nums.length;
		if (Math.abs(nums[len - 1] - nums[0]) == len - 1) return -1 //there is no peek or valley exists

		return binarySearch(nums, 0, len - 1);
	}

	private int binarySearch(int[] nums, int start, int end) {
		int mid = start + (end - start) / 2;

		if ((nums[mid] - nums[mid - 1]) * (nums[mid + 1] - nums[mid] < 0)) {
			return mid;
		}

		int diffOfIndex = mid - start;
		int diffOfValue = Math.abs(nums[mid] - nums[start]);
		if (diffOfIndex == diffOfValue) start = mid;
		else end = mid;
		return binarySearch(nums, start, mid);
	}
}
```

Java implementation of the `O(1)` solution is showed as following:

```
public class Solution {
	public int find(int[] nums) {
		if (nums == null || nums.length == 0) return -1;

		int len = nums.length;
		if (Math.abs(nums[len - 1] - nums[0]) == len - 1) return -1; //no peek or valley exists

		int slope = nums[1] - nums[0];

		if (slope > 0) { //first increase then decrease
			int diff = nums[len - 1] - nums[0];
			return (len - 1 + diff) / 2;
		} else { //first decrease then increase
			int diff = nums[0] - nums[len - 1];
			return (len - 1 + diff) / 2;
		}
	}
}
```
