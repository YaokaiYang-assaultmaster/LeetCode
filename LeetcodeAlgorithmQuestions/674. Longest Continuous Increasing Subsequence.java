class Solution {
    public int findLengthOfLCIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int ret = 0;
        int currLen = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i == 0) {
                currLen = 1;
            } else {
                if (nums[i] > nums[i - 1]) {
                    currLen++;
                } else {
                    currLen = 1;
                }
            }
            ret = Math.max(currLen, ret);
        }
        
        return ret;
    }
}
