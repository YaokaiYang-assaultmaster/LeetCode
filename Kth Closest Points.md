>Given an array containing N points, find the Kth closest points >to the origin in the 2D plane. You can assume K is much >smaller than N and N is very large. 

Notice that the key requirement here are:_"K is much smaller than N. N is very large."_ Definitely the brute force solution by finding distance of all element and then sorting them in `O(nlogn)` is not suitable. It is costlier as we don't need to sort all the n points as we as only concerned for first k points in the sorted list. Based on the optimization principle of cracking the coding interview, we should deal with redundant work, unnecessary work and bottleneck during the process of optimizing an algorithm, __we should avoid the sorting process here since we don't actually need to make the distances in order__. 

An more efficient solution would be to use a __Max-heap of size k__ for maintaining K minimum distances. Keep adding the distance value until the heap reaches size k. Since Max-heap has the nice property of popping out the largest one in `O(1)` time, we could always replace the one with the largest distance with current point which has smaller distance. So the algorithm would be the following:
>1. Keep adding distance value into the heap until it reaches size k. 
>2. If the distance of the new point is less than the current top element of the heap, replace the top element of the heap with the new >distance. This is `O(1)` time. Then the subsequent heapify to update the heap is `O(logk)`. 
>3. Once all elements have been traversed, the top element in the heap is what we want. (Or all the elements if we want the first k >closest points)
The algorithm has a total of `O(nlogk)` time and `O(k)` space. 
Below is the code for the implementation using Java. 
```
public class Point implements Comparable<Point> {
	double x;
	double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getDist() {
		return x * x + y * y;
	}
	
	@Override
	public int compareTo(Point o) {
		return Double.compare(this.getDist(), o.getDist());
	}
}

public class Solution {
	public Point[] kthClosest(Point[] points, int k) {
		Queue<Point> kclosest = new PriorityQueue<>(k, Collections.reverseOrder());

		for (int i = 0; i < points.length; i++) {
			if (kclosest.size() < k) {
				kclosest.offer(points[i]);
			} else {
				if (points[i].getDist() < kclosest.peek().getDist()) {
					kclosest.remove();
					kclosest.offer(points[i]);
				}
			}
		}

		Point[] ret = new Point[k];
		int i = 0;
		for (Point p : kclosest) {
			ret[i++] = p;
		}
		return ret;
	}
}
```


However, this is still not the optimal solution. The extra work we are doing is offering and popping the points into and out of the heap. The heapify process takes $O(logk)$ time each. We could do better without it. In this case, we need to use __QuickSelect__, which could makes us accomplish the goal in `O(n)` time, `O(1)` space. The algorithm is showed in the following:
>1. Partition the points array using a random pivot into 2 parts. 
>2. If the partition pivot index is less than k, partition the second part again. 
>3. If the partition pivot index is greater than k, partition the first part again. 
>4. If the partition pivot index is k, the first half is what we want. 

The implementation using Java is showed as following. 
```
public class Point {
	double x;
	double y;
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getDist() {
		return x * x + y * y;
	}
}

public class Solution {
	public Point kthClosest(Point[] points, int k) {
		int start = 0;
		int end = points.length - 1;

		int index = findKClosest(points, start, end, k);
		//return (0,0) indicates that there is no legitimate result
		return index == Integer.MIN_VALUE ? new Point(0, 0) : points[index];
	}
	
	public int findKClosest(Point[] points, int l, int r, int k) {
		for (int i = 0; i < points.length; i++) {
			System.out.print("(" + points[i].x + "," + points[i].y + ")" + "|");
		}
		if (l < r) {
			int q = randomizedPartition(points, l, r);
			int n = q - l + 1;
			if (n == k) {
				return q;
			} else if (n > k) {
				return findKClosest(points, l, q, k);
			} else {
				return findKClosest(points, q + 1, r, k - n);
			} 
		} else {
			return Integer.MIN_VALUE;
		}
	}

	public int randomizedPartition(Point[] points, int l, int r) {
		int i = (int) Math.round(l + Math.random() * (r - l));
		System.out.println("i:" + i);
		swap(points, i, r);
		return partition (points, l, r);
	}
	
	public void swap(Point[] points, int i, int j) {
		Point tmp = points[i];
		points[i] = points[j];
		points[j] = tmp;
	}

	public int partition (Point[] points, int l, int r) {
		double pivot = points[r].getDist();
		int i = l - 1;
		int j = l;
		
		for (; j < r; j++) {
			if (points[j].getDist() <= pivot) {
				swap(points, ++i, j);
			}
		}
		
		swap(points, i + 1, r);
		return i + 1;
	}
}
```

