public class MinSegmentTree {
	private ArrayList<Integer> minSegmentTree;
	private int n;
	
	public MinSegmentTree(int[] arr) {
		n = arr.length;
		minSegmentTree = new ArrayList<>(2 * n);
		
		for  (int i = 0; i < n; i++) {
			minSegmentTree.add(0);
		}
		
		for (int i = 0; i < n; i++) {
			minSegmentTree.add(arr[i]);
		}
		
		for (int i = n - 1; i > 0; i--) {
			minSegmentTree.set(i, Math.min(minSegmentTree.get(2 * i), minSegmentTree.get(2 * i + 1)));
		}
	}
	
	public void update(int i, int value) {
		i += n;
		minSegmentTree.set(i,  value);
		
		while (i > 1) {
			i /= 2;
			minSegmentTree.set(i, Math.min(minSegmentTree.get(2 * i), minSegmentTree.get(2 * i + 1)));
		}
	}
	
	/**
	 * Get the minimum of range [left, right)
	 */
	public int minimum(int left, int right) {
		left += n;
		right += n;
		int min = Integer.MAX_VALUE;
		
		while (left < right) {
			if ((left & 1) == 1) {
				min = Math.min(min, minSegmentTree.get(left));
				left++;
			}
			
			if ((right & 1) == 1) {
				right--;
				min = Math.min(min,  minSegmentTree.get(right));
			}
			left >>= 1;
			right >>= 1;
		}
		
		return min;
	}
}
