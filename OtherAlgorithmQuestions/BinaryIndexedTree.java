public class BinaryIndexedTree {
	private int[] bitArr;

	// O(nlogn) initialization
//	public BinaryIndexedTree(int[] list) {
//		this.bitArr = new int[list.length + 1];
//		for (int i = 0; i < list.length; i++) {
//			this.update(i, list[i]);
//		}
//	}
	
	public BinaryIndexedTree(int[] list) {
		// O(n) initialization
		this.bitArr = new int[list.length + 1];
		for (int i = 0; i < list.length; i++) {
			this.bitArr[i + 1] = list[i];
		}
		
		for (int i = 1; i < this.bitArr.length; i++) {
			int j = i + (i & -i);
			if (j < this.bitArr.length) {
				this.bitArr[j] += this.bitArr[i];
			}
		}
	}
	
	/**
	 * Add `delta` to elements in `idx` of original array
	 * @param idx index of the element in original array that is going to be updated
	 * @param delta number that will be added to the original element.
	 */
	public void update(int idx, int delta) {
		idx += 1;
		while (idx < this.bitArr.length) {
			this.bitArr[idx] += delta;
			idx = idx + (idx & -idx);
		}
	}
	
	/**
	 * Get the sum of elements in the original array up to index `idx`
	 * @param idx index of the last element that should be summed. 
	 * @return sum of elements from index 0 to `idx`.
	 */
	public int prefixSum(int idx) {
		idx += 1;
		int result = 0;
		while (idx > 0) {
			result += this.bitArr[idx];
			idx = idx - (idx & -idx);
		}
		
		return result;
	}
	
	/**
	 * Get the range sum of elements from original array from index `from_idx` to `to_idx`
	 * @param from_idx start index of element in original array
	 * @param to_idx end index of element in original array
	 * @return range sum of elements from index `from_idx` to `to_idx`
	 */
	public int rangeSum(int from_idx, int to_idx) {
		return prefixSum(to_idx) - prefixSum(from_idx - 1);
	}
}
