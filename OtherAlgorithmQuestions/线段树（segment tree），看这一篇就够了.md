# 定义

线段树（segment tree），顾名思义， 是用来存放给定区间（segment, or interval）内对应信息的一种数据结构。与[树状数组（binary indexed tree）](https://www.jianshu.com/p/5b209c029acd)相似，线段树也用来处理数组相应的区间查询（range query）和元素更新（update）操作。与树状数组不同的是，线段树不止可以适用于区间求和的查询，也可以进行区间最大值，区间最小值（Range Minimum/Maximum Query problem）或者区间异或值的查询。

对应于树状数组，线段树进行更新（update）的操作为`O(logn)`，进行区间查询（range query）的操作也为`O(logn)`。

# 实现原理

从数据结构的角度来说，线段树是用一个__完全二叉树__来存储对应于其每一个区间（segment）的数据。该二叉树的每一个结点中保存着相对应于这一个区间的信息。同时，线段树所使用的这个二叉树是用一个数组保存的，与堆（Heap）的实现方式相同。

例如，给定一个长度为`N`的数组`arr`，其所对应的线段树`T`各个结点的含义如下：    
1. `T`的根结点代表整个数组所在的区间对应的信息，及`arr[0:N]`（__不含N__)所对应的信息。  
2. `T`的每一个叶结点存储对应于输入数组的每一个单个元素构成的区间`arr[i]`所对应的信息，此处`0≤i<N`。  
3. `T`的每一个中间结点存储对应于输入数组某一区间`arr[i:j]`对应的信息，此处`0≤i<j<N`。  

以根结点为例，根结点代表`arr[0:N]`区间所对应的信息，接着根结点被分为两个子树，分别存储`arr[0:(N-1)/2]`及`arr[(N-1)/2+1:N]`两个子区间对应的信息。也就是说，对于每一个结点，其左右子结点分别存储母结点区间拆分为两半之后各自区间的信息。也就是说对于长度为`N`的输入数组，线段树的高度为`logN`。

对于一个线段树来说，其应该支持的两种操作为：  
1. Update：更新输入数组中的某一个元素并对线段树做相应的改变。  
2. Query：用来查询某一区间对应的信息（如最大值，最小值，区间和等）。  

## 线段树的初始化

线段树的初始化是自底向上进行的。从每一个叶子结点开始（也就是原数组中的每一个元素），沿从叶子结点到根结点的路径向上按层构建。在构建的每一步中，对应两个子结点的数据将被用来构建应当存储于它们母结点中的值。每一个中间结点代表它的左右两个子结点对应区间融合过后的大区间所对应的值。这个融合信息的过程可能依所需要处理的问题不同而不同（例如对于保存区间最小值的线段树来说，merge的过程应为`min()`函数，用以取得两个子区间中的最小区间最小值作为当前融合过后的区间的区间最小值）。但从叶子节点（长度为1的区间）到根结点（代表输入的整个区间）更新的这一过程是统一的。  

__注意此处我们对于`segmentTree]`数组的索引从1开始算起__。则对于数组中的任意结点`i`，其左子结点为`2*i`，右子结点为`2*i + 1`，其母结点为`i/2`。

构建线段树的算法描述如下：

```
construct(arr):
  n = length(arr)
  segmentTree = new int[2*n]
  for i from n to 2*n-1:
    segmentTree[i] = arr[i - n]
  for i from n - 1 to 1:
    segmentTree[i] = merge(segmentTree[2*i], segmentTree[2*i+1])
```

例如给定一个输入数组`[1,5,3,7,3,2,5,7]`，其所对应的最小值线段树应如下图所示：

![construct a minimum value segment tree](https://upload-images.jianshu.io/upload_images/2112205-c8f57e983e932332.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

上图所示线段树每一个结点代表的区间则如下图所示：

![segment tree interval representation](https://upload-images.jianshu.io/upload_images/2112205-60ffce49e8afc056.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

如果用其数组表示来说，则数组`segmentTree`中的每一个位置代表的区间如下：

```
segmentTree[1] = arr[0:8)
segmentTree[2] = arr[0:4)
segmentTree[3] = arr[4:8)
segmentTree[4] = arr[0:2)
segmentTree[5] = arr[2:4)
segmentTree[6] = arr[4:6)
segmentTree[7] = arr[6:8)
segmentTree[8] = arr[0]
segmentTree[9] = arr[1]
segmentTree[10] = arr[2]
segmentTree[11] = arr[3]
segmentTree[12] = arr[4]
segmentTree[13] = arr[5]
segmentTree[14] = arr[6]
segmentTree[15] = arr[7]
```

## 更新

更新一个线段树的过程与上述构造线段树的过程相同。当输入数组中位于`i`位置的元素被更新时，我们只需从这一元素对应的叶子结点开始，沿二叉树的路径向上更新至更结点即可。显然，这一过程是一个`O(logn)`的操作。其算法如下。

```
update(i, value):
  i = i + n
  segmentTree[i] = value
  while i > 1:
    i = i / 2
    segmentTree[i] = merge(segmentTree[2*i], segmentTree[2*i+1])
```

例如对于上图中的线段树，如果我们调用`update(5, 6)`，则其更新过程如下所示。

![update segment tree step 1](https://upload-images.jianshu.io/upload_images/2112205-31daf6c2478813d9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![update segment tree step 2](https://upload-images.jianshu.io/upload_images/2112205-4a90633831a1bc4b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![update segment tree step 3](https://upload-images.jianshu.io/upload_images/2112205-e8937d4f639c9d25.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 区间查询

区间查询大体上可以分为3种情况讨论：  
1. 当前结点所代表的区间完全位于给定需要被查询的区间之外，则不应考虑当前结点
2. 当前结点所代表的区间完全位于给定需要被查询的区间之内，则可以直接查看当前结点的母结点
3. 当前结点所代表的区间部分位于需要被查询的区间之内，部分位于其外，则我们先考虑位于区间外的部分，后考虑区间内的（注意总有可能找到完全位于区间内的结点，因为叶子结点的区间长度为1，因此我们总能组合出合适的区间）

以求最小值为例，其算法如下：

```
minimum(left, right):
  left = left + n
  right = right + n
  minimum = Integer.MAX_VALUE
  while left < right:
    if left is odd:
      // left is out of range of parent interval, check value of left node first, then shift it right in the same level
      minimum = min(minimum, segmentTree[left])
      left = left + 1
    if right is odd:
      // right is out of range of current interval, shift it left in the same level and then check the value
      right = right - 1
      minimum = min(minimum, segmentTree[right])
    // move left and right one level up
    left = left / 2
    right = right / 2
```

## n不是2的次方怎么办？

注意上面的讨论中我们由于需要不断二分区间，给定的输入数组的长度`n`为2的次方。那么当`n`不是2的次方，或者说，当`n`无法被完全二分为一些长度为1的区间时，该如何处理呢？

一个简单的方法是在原数组的结尾补0，直到其长度正好为2的次方位置。但事实上这个方法比较低效。最坏情况下，我们需要`O(4n)`的空间来存储相应的线段树。例如，如果输入数组的长度刚好为`2^x+1`，则我们首先需要补0直到数组长度为`2^(x+1) = 2*2^x`为止。那么对于这个补0过后的数组，我们需要的线段树数组的长度为`2*2*2^x = 4*2^x = O(4n)`。

其实上面所说的算法对于`n`不是2的次方的情况同样适用。这也是为什么我在上文中说线段树是一棵__完全二叉树__而非__满二叉树__的原因。

例如对于输入数组`[4,3,9,1,6,7]`，其构造出的线段树应当如下图所示：

![segment tree-not full](https://upload-images.jianshu.io/upload_images/2112205-89dfc5b24d17f93d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

可以看出，在构造过程中我们事实上把一些长度为1的区间直接放在了树的倒数第二层来实现这个线段树。

# Java代码

## Range Minimum Query

```Java
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
```

## Range Sum Query

```Java
public class SumSegmentTree {
	private ArrayList<Integer> sumSegmentTree;
	private int n;
	
	public SumSegmentTree(int[] arr) {
		n = arr.length;
		sumSegmentTree = new ArrayList<>(2 * n);
		
		for  (int i = 0; i < n; i++) {
			sumSegmentTree.add(0);
		}
		
		for (int i = 0; i < n; i++) {
			sumSegmentTree.add(arr[i]);
		}
		
		for (int i = n - 1; i > 0; i--) {
			sumSegmentTree.set(i, sumSegmentTree.get(2 * i) + sumSegmentTree.get(2 * i + 1));
		}
	}
	
	public void update(int i, int value) {
		i += n;
		sumSegmentTree.set(i,  value);
		
		while (i > 1) {
			i /= 2;
			sumSegmentTree.set(i, sumSegmentTree.get(2 * i) + sumSegmentTree.get(2 * i + 1));
		}
	}
	
	/**
	 * Get the sum of range [left, right)
	 */
	public int sum(int left, int right) {
		left += n;
		right += n;
		int sum = 0;
		while (left < right) {
			if ((left & 1) == 1) {
				sum += sumSegmentTree.get(left);
				left++;
			}
			
			if ((right & 1) == 1) {
				right--;
				sum += sumSegmentTree.get(right);
			}
			
			left >>= 1;
			right >>= 1;
		}
		
		return sum;
	}
}
```

> Reference:
> https://www.youtube.com/watch?v=Oq2E2yGadnU
