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
     * Run BFS in a recursive DFS way
    **/
    
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
       
        traverseHelper(root, ret, 0);
        return ret;
    }
    
    private static void traverseHelper(TreeNode root, List<List<Integer>> ret, int height) {
        if (root == null) return;
        
        if (height >= ret.size()) {
            ret.add(new ArrayList<>());
        }
        ret.get(height).add(root.val);
        
        traverseHelper(root.left, ret, height + 1);
        traverseHelper(root.right, ret, height + 1);
    }
    
    
    
    
    /** 
     * Level order traversal is the same as BFS.
     * We want to store the nodes of current level in a FIFO style
     * So what we need is a Queue.
     * Meanwhile, we want to traverse each level seperately
     * So we need a int to keep track of the size of the queue at the beginning of the while loop, which is also the number of the nodes belonged to the current level.
     * We use a for loop to traverse each one of them and store the vals. At the same time, we store their non-null child nodes into the queue from left to right. 
     * O(n) time where n is the number of nodes. O(n/2) space in the worst case(the number of nodes of a complete binary tree)
    **/
    
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        
        if (root == null) return ret;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                TreeNode curr = queue.poll();
                level.add(curr.val);
                if (curr.left != null) {
                    queue.add(curr.left);
                }
                if (curr.right != null) {
                    queue.add(curr.right);
                }
            }
            
            ret.add(new ArrayList<Integer>(level));
        }
        
        return ret;
    }
}
