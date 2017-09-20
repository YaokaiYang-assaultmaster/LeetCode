public class Solution {
    
    /**
     * We want to know the maximum amount of water that can be trapped. 
     * It is obvious that the 2 boundary, which is height[0] and height[height.length - 1] respectively, cannot hold any water
     * Starting from midpoint and traverse to left and right seems like a reasonable solution, but it is hard to decide where to start and stop. And there is to many possible conditions we need to consider. 
     * So we may consider start from 2 boundary of the array and traverse inside the height array. 
     * Based on the bucket theory, we need to always consider from the side with a lower height. 
     * So the algorithm is following:
     * 1. Start from the 2 boundary of the height array and keep 2 pointers for left and right point. 
     * 2. Keep 2 int value leftH and rightH to store the previous height we encountered at 2 sides. 
     * 3. Move the pointer with lower height since this is the upper bound of the bucket. There are 2 conditions:
     * (1) the height next to current point has a greater height, then we cannot increase the total water. Just update the corresponding height of left or right. 
     * (2) the height next to current point has a smaller height, then we can at least hold leftH/rightH - height[pointer] more water.(remember we are always moving from the side with smaller height)
     * 4. Continue until left pointer and right pointer meets. 
     * O(n) time, O(1) space where n is the length of the height array. 
     */
    
    public int trap(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        
        int left = 0;
        int right = height.length - 1;
        int leftH = height[left];
        int rightH = height[right];
        
        int ret = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                left++;
                ret += Math.max(0, leftH - height[left]);
                leftH = Math.max(height[left], leftH);
            } else {
                right--;
                ret += Math.max(0, rightH - height[right]);
                rightH = Math.max(height[right], rightH);
            }
        }
        
        return ret;
    }
}
