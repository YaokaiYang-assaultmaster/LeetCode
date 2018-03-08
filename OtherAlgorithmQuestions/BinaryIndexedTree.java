package implementations;

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
	
	public void update(int idx, int delta) {
		idx += 1;
		while (idx < this.bitArr.length) {
			this.bitArr[idx] += delta;
			idx = idx + (idx & -idx);
		}
	}
	
	public int prefixSum(int idx) {
		if (idx == 0) return 0;
		int result = 0;
		while (idx > 0) {
			result += this.bitArr[idx];
			idx = idx - (idx & -idx);
		}
		
		return result;
	}
	
	public int rangeSum(int from_idx, int to_idx) {
		return prefixSum(to_idx) - prefixSum(from_idx - 1);
	}
}
