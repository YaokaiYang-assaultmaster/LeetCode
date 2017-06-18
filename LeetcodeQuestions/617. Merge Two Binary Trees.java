/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    /** (1) iterative solution
     * In order to merge the 2 binary trees, we need to traverse them in the same pace. 
     * This traverse can be done in BFS or DFS style. 
     * We do it in BFS in this solution. 
     * It is obvious that the key problem here is to maintain the order of traversal.
     * We only want to traverse those nodes that both t1 and t2 have. 
     * For those nodes that only one of them has, simply put the nodes and the entire subtree of the nodes in the corresponding position in the result.  
     * In order to save space, we use t1 as the result, meaning we will modify the nodes in t1 based on traversal status. 
     * The algorithm is showed as following:
     * 1. Put root of t1 and t2 into queue. 
     * 2. Take one node out from queue1 and queue2 as curr1 and curr2 respectively. Sum up the val (since this is the node that both t1 and t2 have) as the val of the new node at this position. 
     * 3. Check for the left child of curr1 and curr2. 
     *  - If both of them have left child, put left child into the 2 queues respectively. 
     *  - If only one of them has left child or both have no left child, set current result's left child as the left child that is not null or null. 
     * 4. Check for the right child of curr1 and curr2. Do the same checking as step3 above. 
     * 5. Check for if the 2 queue is both empty. If so, stop the loop. Otherwise continue. 
     * The time complexity of this algorithm is O(h) where h is the depth of the deepest common nodes t1 and t2 have. 
     * The space complexity of this o(n) where n is the number of the common nodes of t1 and t2. 
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) return t2;
        if (t2 == null) return t1;
        
        Deque<TreeNode> deque1 = new ArrayDeque<>();
        Deque<TreeNode> deque2 = new ArrayDeque<>();
        
        // BFS
        deque1.offer(t1);
        deque2.offer(t2);
        
        // Using t1 as the resulting Binary Tree
        while (!deque1.isEmpty() || !deque2.isEmpty()) {
            TreeNode curr1 = deque1.poll();
            TreeNode curr2 = deque2.poll();
            TreeNode result = curr1;
            result.val = curr1.val + curr2.val;
            
            if (curr1.left == null || curr2.left == null) {
                result.left = curr1.left == null ? curr2.left : curr1.left;
            } else if (curr1.left != null && curr2.left != null) {
                deque1.offer(curr1.left);
                deque2.offer(curr2.left);
            }
            
            if (curr1.right == null || curr2.right == null) {
                result.right = curr1.right == null ? curr2.right : curr1.right;
            } else if (curr1.right != null  && curr2.right != null) {
                deque1.offer(curr1.right);
                deque2.offer(curr2.right);
            }
        }
        
        return t1;
    }
}
