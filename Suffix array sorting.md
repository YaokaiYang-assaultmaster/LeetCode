Given a suffix array. E.g. `int[] text = {10, 20, 30, 25}`, then `suffix[0] = {10, 20, 30, 25}`, `suffix[1] = {20, 30, 25}`, `succix[2] = {30, 25}`, `suffix[3] = {25}`.
Then sort those arrays using _lexical order_. The result would be `suffix[0] < suffix[1] < suffix[3] < suffix[2]`. 

Implement the code to sort suffix array. `Input: int[] text`. `Output: int[] sorted_suffix_array`.
E.g. input: `int[] text = {10, 20, 30, 25}`, output: `int[] sorted_suffix_array = {0, 1, 3, 2}`.

- Analysis:

	For this question, we need to sort a suffix arrays. In order to sort them, we need to compare each element. Thus we need to overwrite the comparator for sort. 
	In order to sort them based on suffix arrays, we need to create a wrapper class storing the starting index and pointer to the text array. Thus we can use collections.sort()
	to sort this wrapper class. 
`O(n)` space. `O((n ^ 2)logn)` time ==> `O(nlogn)` sort, `O(n)` for each comparison, `O(n * n logn)` total. 

Here is the implementation in Java:
```
public class Solution {
	class Suffix {
		int index;
		int[] array;

		public Suffix(int index, int[] array) {
			this.index = index;
			this.array = array;
		}
	}

	public int[] suffixSort(int[] text) {
		if (text == null || text.length == 0) return new int[]{};

		List<Suffix> suffixList = new ArrayList<>();
		for(int i = 0; i < text.length; i++) {
			suffixList.add(new Suffix(i, text));
		}

		int l = text.length;
		Collections.sort(suffixList, new Comparator<Suffix>() {
			@Override
			public int compare(Suffix s1, Suffix s2) {
				int i = 0;
				for(; i < Math.min(l - s1.index, l - s2.index); i++) {
					if(s1.array[i] < s2.array[i]) {
						return -1;
					}
					else if(s1.array[i] > s2.array[i]) {
						return 1;
					}
				}
				return i == l - s1.index ? -1 : 1;
			}
		});

		int[] ret = new int[l];
		int j = 0;

		for(Suffix s : suffixList) {
			ret[j++] = s.index;
		}

		return ret;
	}
}
```
