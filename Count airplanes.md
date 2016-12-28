> Given the take off and landing time of airplanes, described using a list of intervals. How many airplanes are there in the sky simultaneously?
> Note that if multiple planes take off and land at the same time, landing has priority. 
> E.g. for a list of intervals like `[[1, 10], [2, 3], [5, 8], [4, 7]]`, return `3`

- Analysis:
This problem asks us to calculate how many airplanes are there at most simultaneously in the sky. That is, to check how many lines are there
sumultaneously in the sky, if we treat each interval as a line with start point and end point. 
In this case, we need to construct a wrapper class to store the point, in which contains time and flag. Flag indicates if this point is a start
point or an end point. 

So the algorithm is following:
1. Store all the start and end points into a list. 
2. Sort the list based on time. End point has priority in case of same time based on the requirement. 
3. Count the number of lines existing simultaneously. 
Suppose the size of the airplanes list is n. 
The time complexity of this code is `O(nlogn)`. For adding the points into the list, it is `O(2n)`. Sorting the Point list is `O(2nlog(2n))` = `O(nlogn)`.
Then traverst the point list need `O(2n)`. So the total time would be `O(nlogn)`. 
The space complexity of this code is `O(2n)` = `O(n)`. Since we need to store all the start and end points into the list. 

The implementation in Java is showed as following:

``` Java
/**
 * Definition of Interval:
 * public class Interval {
 *  int start, end;
 *	Interval(int start, int end) {
 *		this.start = start;
 * 		this.end = end;
 *	}
 * }
 */


class Point {
	int time, flag;
	Point (int time, int flag) {
		this.time = time;
		this.flag = flag;
	}

	public static Comparator<Point> PointComparator = new Comparator<Point>() {
		@Override
		public int compare(Point p1, Point p2) {
			if (p1.time == p2.time) {
				return p1.flag - p2.flag;
			} else {
				return p1.time - p2.time;
			}
		}
	};
}

class Solution {
	public int countOfAirplanes(List<Interval> airplanes) {
		List<Point> lines = new ArrayList<>(airplanes.size() * 2);
		for(Interval i : airplanes) {
			lines.add(new Point(i.start, 1));
			lines.add(new Point(i.end, 0));
		}
		Collections.sort(lines, Point.PointComparator);
		int count = 0;
		int ret = 0;
		for(Point p : lines) {
			if(p.flag == 1) {
				count++;
			} else{
				count--;
			}
			ret = Math.max(count, ret);
		}
		return ret;
	}
}
```

