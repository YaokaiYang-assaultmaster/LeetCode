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
    
    /**(1) Recursive method.
     * Since it's a binary tree question, recursion might be the apporpriate solution. 
     * To constructr a binary tree is to find out the root of each level. 
     * Thus for this question, the basic idea should be to find out the root of each level. 
     * For the first level, the root is simply the last node in postorder traversal. 
     * And based on this point, we can devide the inorder array into 2 parts. 
     * The left part of the array is the left subtree of root, and the right part of the array is the right subtree of the root.
     * Thus based on the length of left and right subtree, we could get the left sub-root and right-root again. 
     * Do it  recursively and we got the whole tree. 
     
     * The time complexity would be O(n/2 + n / 4 + n / 8 + ... + 1) = O(n) time on average considering the binary is approximately balance. Worst case O(n + n - 1 + n - 2 + ... + 1) = O(n^2) when the binary tree is like a linkedlist. 
     * See here for proof: https://math.stackexchange.com/questions/401937/how-is-nn-2n-4-1-equal-to-2n-1-using-the-formula-for-geometric-series. 
     * A small optimization to the time would be to add a HashMap store each number and its corresponding index of inorder array. Thus we shortens the time of looking up root position of each level to O(1), thus make the total time to be O(n) stably. But the space would be O(n) in this case. 
     * See the second solution for an implementation. 
     
     * The space complexity would be O(1) without the recursion stack. And O(logn) on average with the recursion stack. O(n) worst case. These conditions are the same with time. 
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder == null || postorder == null) {
            return null;
        }
        if (inorder.length == 0 || postorder.length == 0) {
            return null;
        }
        if (inorder.length != postorder.length) {
            return null;
        }
        
        return buildTree(inorder, postorder, 0, inorder.length - 1, 0, postorder.length - 1);
    }
    
    public TreeNode buildTree(int[] inorder, int[] postorder, int startInorder, int endInorder, int startPostorder, int endPostorder) {
        // Notice that the length of inorder and postorder array in this function should always be the same.
        // Since they only contain different traversal sequence of the same binary tree.  
    
        // If the length of inorder array and postorder array is 0, return null since it indicates there is no node in current branch. 
        if (endInorder < startInorder) {
            return null;
        }
        // If the length of inorder array and postorder array is 1, return the node directly. 
        if (endInorder == startInorder) {
            return new TreeNode(inorder[startInorder]);
        }
        
        int rootNum = postorder[endPostorder];
        int rootPos = -1;
        TreeNode root = new TreeNode(rootNum);
        
        for (int i = startInorder; i <= endInorder; i++) {
            if (inorder[i] == rootNum) {
                rootPos = i;
                break;
            }
        }
        
        int leftLen = rootPos - startInorder;
        
        root.left = buildTree(inorder, postorder, startInorder, startInorder + leftLen - 1, startPostorder, startPostorder + leftLen - 1);
        
        root.right = buildTree(inorder, postorder, startInorder + leftLen + 1, endInorder, startPostorder + leftLen,  endPostorder - 1);
        
        return root;
    }
    
    /**(2) Recursion with HashMap
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder == null || postorder == null) {
            return null;
        }
        if (inorder.length == 0 || postorder.length == 0) {
            return null;
        }
        if (inorder.length != postorder.length) {
            return null;
        }
        
        Map<Integer, Integer> indexMap = new HashMap<>();
        indexMap = buildMap(inorder);
        
        return buildTree(inorder, postorder, 0, inorder.length - 1, 0, postorder.length - 1, indexMap);
    }
    
    public Map<Integer, Integer> buildMap(int[] inorder) {
        Map<Integer, Integer> result = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            result.put(inorder[i], i);
        }
        
        return result;
    }
    
    public TreeNode buildTree(int[] inorder, int[] postorder, int startInorder, int endInorder, int startPostorder, int endPostorder, Map<Integer, Integer> indexMap) {
        // Notice that the length of inorder and postorder array in this function should always be the same.
        // Since they only contain different traversal sequence of the same binary tree.  
    
        // If the length of inorder array and postorder array is 0, return null since it indicates there is no node in current branch. 
        if (endInorder < startInorder) {
            return null;
        }
        // If the length of inorder array and postorder array is 1, return the node directly. 
        if (endInorder == startInorder) {
            return new TreeNode(inorder[startInorder]);
        }
        
        int rootNum = postorder[endPostorder];
        int rootPos = indexMap.get(rootNum);
        TreeNode root = new TreeNode(rootNum);
        
        int leftLen = rootPos - startInorder;
        
        root.left = buildTree(inorder, postorder, startInorder, startInorder + leftLen - 1, startPostorder, startPostorder + leftLen - 1, indexMap);
        
        root.right = buildTree(inorder, postorder, startInorder + leftLen + 1, endInorder, startPostorder + leftLen,  endPostorder - 1, indexMap);
        
        return root;
    }
    
}
