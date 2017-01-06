> A binary tree inorder successor is the next node in inordr traversal of the binary tree. Inorder successor is NULL for the last node in inorder traversal. 
> In binary search tree, inorder successor of an input node can also be defined as the node with the smallest key greater than the key of input node. So it is sometimes important to find next node in sorted order. 

#### Method 1 (Using parent pointer)

In this method, we assume that every node has parent pointer. 

This alogorithm is divided into two cases on the basis of right subtree of the input node being empty or not. 

1. if right subtree of node is not NULL, then the successor lies in the right subtree. Go to right subtree and return the node with minimum key value in the right subtree. 
2. if right subtree of node is NULL, then successor is one of the ancestors. Travel up using the parent pointer until you see a node which is left child of it's parent. The parent of such a node is the successor. 

An implementation in Java is showed as following. 

```
class TreeNode {
	int val;
	Node left, right, parent;
	
	Node(int val) {
		this.val = val;
		left = right = parent = null;
	}
}

public class Solution {
	public TreeNode inOrderSuccessor(TreeNode target) {
		if (n.right != null) {
			TreeNode curr = target.right;
			while (curr.left != null) {
				curr = curr.left;
			}
			return curr;
		}
		
		//target node has no right subtree
		TreeNode curr = target;
		TreeNode p = curr.parent;
		while (p != null && curr == p.right) {
			curr = p;
			p = curr.parent;
		}
		return p;
	}
}

```


#### Method 2 (Searching for root)

If there is no parent pointer, the algorithm is divided into 2 cases on the basis of right subtree of the input node being empty or not. 

1. If right subtree of node is not NULL, then the successor lies in right subtree. Go to right subtree and return the node with minimum key value in the right subtree. 
2. If tright subtree of node is NULL, then start from root and search. Travel down the tree, if the node's val is greater than root's val then go right side, otherwise go to left side. 

The implementation in Java is showed as following. 

```
public class Solution {
	public TreeNode inOrderSuccessor(TreeNode root, TreeNode target) {
		TreeNode curr = target;
		if (curr.right != null) {
			curr = curr.right;
			while (curr.left != null) {
				curr = curr.left;
			}
			
			return curr;
		}
		
		//right subtree of target is NULL
		TreeNode succ;
		curr = root;
		while (curr != null) {
			if (curr.val > target.val) {
				succ = curr;
				curr = curr.left;
			} else if (curr.val < target.val) {
				curr = curr.right;
			} else {
				break;
			}
		}
		
		return succ;
	}
}
```

The time complexity for both algorithm is `O(h)` where h is the height of the tree. 
