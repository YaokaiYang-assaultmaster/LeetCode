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
    
    /**
     * Originally I thought I have to swap the actual nodes, making sure their parents, left children and right children have the right pointers. However, that would be complex to keep record of so many relatives of the nodes. Indeed, the only thing I have to do is exchange their values. 
     * Thus a in-order-traversal would be sufficient for this purpose. 
     * Just check which nodes are not "inorder".
     * In this case, not "inorder" means that one of the node is smaller than its predecessor (the one that is being swapped to the right), and the other node is greater than its postdecrssor(the one that is being swapped to the left).
     * However, considering that we normally only keep track of the previous visited node in the tree during the traversal process, it is not practical to check for both the previous value and the next value. Thus, we need to think of of way to check for the swapped nodes based on only the previous value. 
     * Noticing that the tree we are checking is a Binary Search Tree. And a great value is swapped to left, and a small value is swapped to right. And considering about the in-order traversal result, we know we would first encounter with the greater value, then the smaller one. And thus for the first pair node satisfying prev.val > curr.val, prev is the swapped one. And for the last pair nodes satisfying prev.val > curr.val, curr is the swapped one (Why last pair? Because the first pair is the one we just discuessed, in which prev is the swapped one). 
     * Nonetheless, we do have to take into consideration the corner case that the 2 swapped nodes are in the same pair, and that's why we cannot use if/else if block to do the logic. 
     * Thus we can get both the first and second swapped node in one pass. 
     * This is a worst case O(n) space solution since we use iterative inorder traversal, a O(logn) to O(n) size stack is used. 
     * Time is O(n) as well. 
     * n is the number of nodes. 
     */
    public void recoverTree(TreeNode root) {
        if (root == null) return;
        
        Deque<TreeNode> deque = new ArrayDeque<>();
        TreeNode curr = root;
        
        TreeNode prev = new TreeNode(Integer.MIN_VALUE);
        
        TreeNode first = null;
        TreeNode second = null;
        
        while (!deque.isEmpty() || curr != null) {
            while (curr != null) {
                deque.push(curr);
                curr = curr.left;
            }
            
            curr = deque.pop();
            
            if (first == null && prev.val >= curr.val) {
                first = prev;
            }
            if (first != null && prev.val >= curr.val) {
                second = curr;
            }
            
            prev = curr;
            curr = curr.right;
        }
        
        int tmp = first.val;
        first.val = second.val;
        second.val = tmp;
        
    }
    
    
    
    
    /**
     * In order to solve this question in O(1) space, we need to use Morris traversal of a binary tree, which takes exactly O(1) space and perform just the same function like in-order-traversal. 
     */
    public void recoverTree(TreeNode root) {
        if (root == null) {
            return;
        }
        
        TreeNode[] candidates = null;
        TreeNode prev = null;
        
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left == null) {
                candidates = checkSeq(prev, curr, candidates);
                prev = curr;
                curr = curr.right;
            } 
            else { // curr.left != null
                TreeNode tmp = curr.left;
                while (tmp.right != null && tmp.right != curr) { //find right most one in the left subtree
                    tmp = tmp.right;
                }
                if (tmp.right == null) { //left subtree of curr haven't been visited yet
                    tmp.right = curr;
                    curr = curr.left;
                }
                else { //all nodes in curr's left subtree have been visited, go back to parent.
                    candidates = checkSeq(prev, curr, candidates);
                    prev = curr;
                    curr = curr.right;
                    tmp.right = null;
                }
            }
        }
        
        int t = candidates[0].val;
        candidates[0].val = candidates[1].val;
        candidates[1].val = t;
    }
    
    private TreeNode[] checkSeq(TreeNode prev, TreeNode curr, TreeNode[] candidates) {
        if (prev == null) return candidates;
        if (prev.val > curr.val) {
            if (candidates == null) {
                candidates = new TreeNode[]{prev, curr};
            }
            else {
                candidates[1] = curr;
            }
        }
        
        return candidates;
    }
}
