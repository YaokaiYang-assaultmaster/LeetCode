This question is a follow up for question for [Suffix array sorting](https://github.com/YaokaiYang-assaultmaster/LeetCode/blob/master/Suffix%20array%20sorting.md). 
Based on the output of last question, we are given an input array `int[] substext`. We want to check for the existance of this __subText__ in the text array. We already have the output __sorted_suffix_array__. 
The output of this question is a boolean value `isExist` indicating the existance of the `subText` in the `text`. 

The program need to run in time less than `O(N^2)`

- Analysis:

  For this question, since we are going to find something that is already sorted, it is possible that binary search is suitable. Since the `sorted_suffix_array` is based on lexical order,
  it is possible to use binary search for each position, i.e. First binary search for the first position in subtext, then second, then third. 

The time complexity for this code is `O(mlogn)` where `O(logn)` is for the binary search in length n array and `O(m)` is for checking the length m subarray with subText in each binary search iteration. 
The space complexity is `O(1)` without considering the sortedSuffix array since we are only using constant number of variables and passing pointers each time. 

The implementation in Java is showed as following:

```
public class Solution {
	public boolean isExistSubText(int[] text, int[] subText) {
		//corner cases
		if(text == null || text.length == 0) return false;
		if(subText == null || subText.length == 0) return true;

		int[] sortedSuffix = suffixSort(text);

		return binarySearch(text, subText, sortedSuffix, 0, text.length - 1);

	}

	private boolean binarySearch(int[] text, int[] subText, int[] sortedSuffix, int start, int end) {
		int m = subText.length;
		while(end >= start) {
			int mid = start + (end - start) / 2;
			int cmp = compare(text, sortedSuffix[mid], m, subText);
			if(cmp == 0) {
				return true;
			}
			else if(cmp < 0) {
				start = mid + 1;
			}
			else {
				end = mid - 1;
			}
		}

		return false;
	}

	private int compare(int[] text, int start, int length, int[] subText) {
		if (start + length >= text.length) {
			return text[start] > subText[0] ? 1 : -1;
		}

		int p1 = start, p2 = 0;

		while (p1 < start + length && p2 < length) {
			if (text[p1] != subText[p2]) {
				return text[p1] - subText[p2];
			}
			else {
				p1++;
				p2++;
			}
		}

		return 0;
	}
}
```

Note suffixSort() function is in [Suffix array sorting](https://github.com/YaokaiYang-assaultmaster/LeetCode/blob/master/Suffix%20array%20sorting.md).
I copied is as following in order for quick reference. 

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
