> Given a array of directions expressed in 'N', 'S', 'E' and 'W', assume we always only go 1 step far in the directions and we are always start from (0, 0), find out if we have stepped on the same point twice. 
> Follow up: If stepped on the same point twice, can we find out if there is a cross(rectangular) happened?

## Analysis:

In order to describe the whole path, we apprently need to implement a new class which could demonstrate the indexes.  

Meanwhile, checing if we have stepped on a specific point could be naturally solved using Hashing. Thus we have to _Override_ the `.equals(Object obj)` method and `.hashCode()` method of our new class.   
    
To know if a specific point have been stepped on, we could use a HashSet to solve all previously passed points. However, considering we also want to know if there is a cross(rectangular), we need to keep track of the direction as well. Thus a HashMap would be more convenient for we could use point as the key and store direction as the value in it. 
    
Since we only want to check rectangular, we could use 0 and 1 to describe the direction to which a point have been passed. If a point have been passed by both 0 and 1, then a cross happened. 

__Keep in mind that HashSet and HashMap both use `.equals(Object obj)` and `.hashCode()` to check for equality of 2 Objects. Thus we have to make sure for 2 points with the same `x` and `y` value, these 2 functions should return the same results(i.e. either equal or not equal).__
 
### Time and space

We have to iterate through the whole input array in the worst case, thus the time complexity is `O(n)`. 
We have to store every Point into the HashMap(HashSet) as well, thus the space complexity is `O(n)`.

## Code

```Java
import java.util.*;

/**
 * Given a list of movement indicated by direction, find out if a point have been passed for more
 * than one times.
 * Meanwhile, find out if a point have been crossed (rectangular), i.e. passed by all of the four directions.
 */
public class PassingPath {
	class Point {
		int x;
		int y;
		// if crossed by step left<->right, set dir to 1. Otherwise, set dir to 0.
		// Thus when there is a point with the same x and y got passed by step with dir
		// of 0 and 1, indicated by dir1 & dir2 = 1, a cross happens.
		
		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override 
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (!(obj instanceof Point)) {
				return false;
			}
			
			Point point = (Point)obj;
			if (this.x == point.x && this.y == point.y) {
				return true;
			} else {
				return false;
			}
		}
		
		@Override
		public int hashCode() {
			int result = 17;
			result = 31 * result + 31 * this.x;
			result = 31 * result + 31 * this.y;
			return result ;
		}
		
		@Override
		public String toString() {
			return "(" + Integer.toString(this.x) + ", " + Integer.toString(this.y) + ")";
		}
	}
	
	public PassingPath() {
		super();
	}
	
	// input steps is an array of steps indicated by direction: N, S, E, W
	// Always start from (0,0)
	// the return value could be 0, 1, -1
	// 0 indicates no point has been stepped on twice
	// 1 indicates stepped and crossed(rectangular)
	// -1 indicates stepped and no cross
	public int checkPassing(char[] steps) {
		Map<Point, Integer> map = new HashMap<Point,Integer>();
		Point prev = new Point(0, 0);
		for (int i = 0; i < steps.length; i++) {
			char step = steps[i];
			if (i == 0) {
				Point start;
				Point nextStep;
				if (step == 'N' || step == 'S') {
					start = prev;
					if (step == 'N') {
						nextStep = new Point(0, 1);
					} else {
						nextStep = new Point(0, -1);
					}
					map.put(start, 0);
					map.put(nextStep, 0);
				} else {
					start = prev;
					if (step == 'E') {
						nextStep = new Point(1, 0);
					} else {
						nextStep = new Point(-1, 0);
					}
					map.put(start, 1);
					map.put(nextStep, 1);
				}
				prev = nextStep;
			} else {
				Point thisStep;
				int dir = 0;
				if (step == 'N') {
					thisStep = new Point(prev.x, prev.y + 1);
				} else if (step == 'S') {
					thisStep = new Point(prev.x, prev.y - 1);
				} else if (step == 'E') {
					thisStep = new Point(prev.x + 1, prev.y);
					dir = 1;
				} else {
					thisStep = new Point(prev.x - 1, prev.y);
					dir = 1;
				}
				if (map.containsKey(thisStep)) {
					if ((map.get(thisStep) ^ dir) == 1) {
						return 1;
					} else {
						return -1;
					}
				}
				
				map.put(thisStep, dir);
				prev = thisStep;
			}
		}
		
		return 0;
	}
}

```
