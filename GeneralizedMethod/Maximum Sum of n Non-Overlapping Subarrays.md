This is a generalized method for [689. Maximum Sum of 3 Non-Overlapping Subarrays
](https://github.com/YaokaiYang-assaultmaster/LeetCode/blob/master/LeetcodeAlgorithmQuestions/689.%20Maximum%20Sum%20of%203%20Non-Overlapping%20Subarrays.md).

In this solution, `dp[i][j]` is used for recording for ith interval, what are the max sums for first j numbers in each position. And `index[i][j]` is used for recording for ith interval, what are the current start index for first j numbers that made up the max sum. 

Thus after the searching ends, `dp[n][nums.length]` stores the max sum we can get and `index[n][nums.length]` stores the start index of last interval for the max sum. And now we can search backwards for each previous start index based on the start index of current interval. Because the start index of previous interval is the index stored in `index[i - 1][current start index]`, which is the max sum of the subarray before current start index. 

Run time complexity is `O(n * len)` where `n` is the number of intervals needed and len is the length of input array. 

Space complexity is `O(n * len)` as well. 

```
class MaxSumOfThreeSubarrays {
	/**
	 * 
	 * @param nums input array of numbers
	 * @param k length of each interval
	 * @param n number of intervals 
	 * @return start index of each interval that has the maximum sum
	 */
    public int[] maxSumOfThreeSubarrays(int[] nums, int k, int n) {
        int[][] dp = new int[n + 1][nums.length + 1];
        int[][] index = new int[n + 1][nums.length + 1];
        int tot = 0;
        // prefix sum
        int[] sum = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            sum[i + 1] = nums[i] + sum[i];
        }
        
        for (int i = 1; i <= n; i++) {
            for (int j = k - 1; j < nums.length; j++) {
                int tmpMax = sum[j + 1] - sum[j - k + 1] + dp[i - 1][j - k + 1];
                if (tmpMax > dp[i][j]) {
                    dp[i][j + 1] = tmpMax;
                    index[i][j + 1] = j - k + 1;
                } else {
                    dp[i][j + 1] = dp[i][j];
                    index[i][j + 1] = index[i][j];
                }
            }
        }
        
        int[] ret = new int[n];
        int prev = nums.length;
        for (int i = n; i > 0; i--) {
            ret[i - 1] = index[i][prev];
            prev = ret[i - 1];
        }
        
        return ret;
    }
}
```
