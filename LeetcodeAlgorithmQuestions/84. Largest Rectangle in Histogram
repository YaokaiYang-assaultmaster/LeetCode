public class Solution {
    /**
     * Scan the array from left to right.
     * Store heights while they are monotically increasing. 
     * While we meet a value that is less than its previous one, we look back and calculate the area. 
     * O(n) time (actually O(2 * n) since every element is put in and take out of the deque once.).
     * O(n) space.
     * n is the length of the array.
     */
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) return 0;
        
        Deque<Integer> deque = new ArrayDeque<>();
        int max = Integer.MIN_VALUE;
        for (int i = 0; i <= heights.length; i++) {
            int curVal = i == heights.length ? Integer.MIN_VALUE : heights[i];
            //set curVal to minimum value to guarantee the last element in the deque to be included in the calculation. 
            
            while (!deque.isEmpty() && heights[deque.peekLast()] > curVal) {
                //We are actually guarantee that the heights whose index is stored in the deque to be monotonic increasing.
                //Then we calculate in decreasing order of the indexes stored in the deque.
                int height = heights[deque.pollLast()];
                
                //if the deque is empty, that means current value is the smallest among all the value we have scanned. 
                //so the left bound would be 0.
                int leftBound = deque.isEmpty() ? 0 : deque.peekLast() + 1;
                int rightBound = i;
                max = Math.max(max, height * (rightBound - leftBound));
            }
            
            //push current element into deque
            deque.offerLast(i);
        }
        
        return max;
    }
}
