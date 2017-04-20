> Write an __Inorder__ iterator for a binary tree with next() and hasNext() function. 

- Analysis:

  An inorder iterator is similar to inorder traversal of a binary tree. However, for the iterator, we just have to keep a state for the current node. Check [here](https://github.com/YaokaiYang-assaultmaster/LeetCode/blob/master/173.%20Binary%20Search%20Tree%20Iterator) for a solution with similar thought but using curr pointer. In this method we keep a next pointer which is always pointing to next node during the iteration. Once next becomes null, there is no more nodes. 

Java implementation is showed as following:

```
public class TreeNode {
	int val;
	TreeNode left;
	TreeNode right;

	public TreeNode (int val) {
		this.val = val;
		left = null;
		right = null;
	}
}

public class TreeIterator {
	private TreeNode next;
	private Deque<TreeNode> stack;
	public BSTIterator (TreeNode root) {
		next = root;
		if (next == null) {
			return;
		}
		
		stack = new ArrayDeque<>();
		while (next != null) {
			stack.push(next);
			next = next.left;
		}
		next = stack.pop();
	}
	
	public boolean hasNext() {
		return next != null;
	}
	
	public int next() {
		if (!hasNext()) return Integer.MIN_VALUE;
		
		TreeNode ret = next;
		next = next.right;
		while (next != null) {
			stack.push(next);
			next = next.left;
		}
		next = stack.isEmpty() ? null : stack.pop();
		
		return ret.val;
	}
}
```
