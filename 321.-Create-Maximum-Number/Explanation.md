简而言之，对于本题我们需要解决两个子问题：
1. 给出一个array，如何从中选取i个数使得这i个数是所有可能的组合之中最大的
   比如`[9,5,8,6,3,2]`, `i = 2`，得到`[9, 8]`
2. 给出两个数组，如何合并形成一个最大数组
   比如`[9,5,3]`和`[6,4]`合并为`[9,6,5,4,3]`

##Solution

那么我们分步先来解决较为简单的问题

### No.1

Given an array, create the maximum number with length k.
To solve this question, gready algorithm with the help of a stack would be possible. The algorithm is like following:

>- Create a stack with length k
>- loop through the array nums
>  - pop the top of the stack if it is smaller than nums[i] until:
>    1. the stack is empty
>    2. the digits left in nums is not enough to fill a stack of length k.
>  - if stack size < k, push nums[i] into stack
>- return stack

Since the stack size is known to be k, it is easy to use an array to mimic the stack
The time complexity is `O(n)` since each digit could be pushed and popped at most once.

```Java
public int[] maxArray(int[] nums, int k) {
  int n = nums.length;
  int[] ret = new int[k];
  
  for (int i = 0, j = 0; i < n; i++) {
    while (n - i + j > k && k > 0 && ret[j - 1] < nums[i]) {
      j--;
    }
    if (j < k) {
      ret[j++] = nums[i];
    }
  }
  
  return ret;
}
```

### No.2
Given 2 array of length m and n, create a maximum number of length `k = m + n`.<br>
Still, gready seems to be a good choice to this question. We have k decisions to make in total, 
each time we need to decide from which of the two original arrays do we get `ret[i]`. It is obvious that we should choose the larger one.
But what if they are equal?<br>
This is not that obvious, what we should do is to check the number after it. For example:
>`num1 = [6,7]`<br>
>`nums2 = [6,0,5]`<br>
>`k = 5`<br>
>`ret = [6,7,6,0,5]`<br>
We decide to choose `6` from nums1 since `7 > 0`. What if they are equal again? We continue to look at the next digits until they are not equal.
If all digits are equal, then it does not matter which one to choose. Due to look next until not equal, the time complexity is `O(m*n)`.

```Java
public int[] merge(int[] nums1, int[] nums2, int k) {
  int[] ret = new int[k];
  for (int i = 0, j = 0, r = 0; r < k; r++) {
    ret[r] = greater(nums1, i, nums2, j) ? nums1[i] : nums2[j];
  }
  
  return ret;
}

public boolean greater(int[] nums1, int i, int[] nums2, int j) {
  while (i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) {
    i++;
    j++;
  }
  
  return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);
}
```

### Final Solution
Now we have solved the 2 subproblems, let's go back to the original problem. We divide 'k' in to two parts, `i` and `k - i`. 
We then find the maximum number of length `i` from one array and maximum number of length `k - i` from another array using the algorithm in No.1.
Then we combine them into one array using algorithm in No.2.<br>
After that we compare this result with the result we have and store the larger one as the final answer.

```Java
public int[] maxNumber(int[] nums1, int[] nums2, int k) {
  int[] ret = new int[k];
  int m = nums1.length;
  int n = nums2.length;
  for (int i = Math.max(0, k - n); i < k && i < m; i++) {
  	//if k >m, there will be at least k - m elements chosen from nums1
    int[] candidate = merge(maxArray(nums1, i), maxArray(nums2, k - i), k);
    if (greater(candidate, 0, ret, 0)) {
      ret = candidate;
    }
  }
  
  return ret;
}

public int[] maxArray(int[] nums, int k) {
  int[] ret = new int[k];
  int n = nums.length;
  for (int i = 0, j = 0; i < n; i++) {
    while (n - i + j > k && j > 0 && ret[j - 1] < nums[i]) {
      j--;
    }
    if (j < k) {
      ret[j++] = nums[i];
    }
  }
  
  return ret;
}

public int[] merge(int[] nums1, int[] nums2, int k) {
  int[] ret = new int[k];
  for (int i = 0, j = 0, r = 0; r < k; r++) {
    ret[r] = greater(nums1, i, nums2, j) ? nums1[i++] : nums2[j++];
  }
  
  return ret;
}

public boolean greater(int[] nums1, int i, int[] nums2, int j) {
  while (i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) {
    i++;
    j++;
  }
  
  return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);
}
```
