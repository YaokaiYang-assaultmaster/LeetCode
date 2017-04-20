*For example, there is an array [1,2,5,7,6,4,9], the subarray need to be sorted is [5,7,6,4].* 

The brute force algorithm would be first sort the array and compare the sorted array and original array. Those position with different number 
should be the subarray that need to be sorted. This is O(nlogn) time where n is the length of the array. 

However, we can optimize it. Since we are only looking for poisition with different number in the original array and sorted array, sort the array
definitely means unnecessary work. So wen would like to avoid this in order to develop a efficient algorithm. 

What we really need is in fact 2 pointers, one at the beginning and one at the end of the subarray. 

So it would be reasonable to walk through the array twice, looking for each of the index in one time. 

Then the code would be following:

``` Java
public class Solution {
  public int[] unsortedSubarray(int[] nums) {
    if (nums == null || nums.length == 0) {
      return new int[]{};
    }
    
    int p1 = 0;
    int p2 = 0;
    for (int i = 0, max = Integer.MIN_VALUE; i < nums.length; i++) {
      if (nums[i] > max) {
        max = nums[i]
      } else if (nums[i] < max) {
        p2 = i;
      }
    }
    
    for (int i = nums.length - 1, min = Integer.MAX_VALUE; i >= 0; i++) {
      if (nums[i] < min) {
        min = nums[i];
      } else if (nums[i] > min) {
        p1 = min;
      }
    }
    
    int[] ret = new int[p2 - p1 + 1];
    for (int i = p1; i <= p2; i++) {
      ret[i - p1] = nums[i];
    }
    
    return ret;
    
  }
}
```

