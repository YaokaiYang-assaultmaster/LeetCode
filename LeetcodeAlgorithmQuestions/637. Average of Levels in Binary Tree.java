/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    /**
     * Straight idea. BFS and calculating the average for every level.
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.offer(root);
        int level = 0;
        while (!deque.isEmpty()) {
            int currBreadth = deque.size();
            ret.add(new Double(0));
            for (int i = 0; i < currBreadth; i++) {
                TreeNode currNode = deque.poll();
                ret.set(level, ret.get(level) + new Double(currNode.val));
                if (currNode.left != null) {
                    deque.offer(currNode.left);
                }
                if (currNode.right != null) {
                    deque.offer(currNode.right);
                }
            }
            ret.set(level, ret.get(level) / currBreadth);
            level++;
        }

        return ret;
    }
}
