> Given a 2D matrix, in which there are some cells being marked. Marked cells are represented by 0 and unmarked ones are 1. Return the number of squares that can be constructed only containing the unmarked cells. Note that each cell itself can be viewed as a square of side length 1. 
> E.g. For input `[[1,1],[1,0]]` the total number of squares is 3. For input `[[1,1],[1,1]]`, the result should be 5. 

# Analysis

If we use a 2D `count[][]` array to store the number of squares using a cell whose index is `(i, j)` as the lower right corner for the square, then we could have a induction equation between `count[i][j]` and `count[i-1][j-1]`, `count[i-1][j]` and `count[i][j-1]`. The equation is: `count[i][j] = min(count[i-1][j-1], count[i-1][j], count[i][j-1]) + 1`. And the number in `count[][]` actually also represents the largest number of cells contained in the side of the square. 

After we have this `count[][]`, we simply add up all numbers in it to get the total number of squares.  

# Java solution

```Java
public class NumOfSquares {
	public int numberOfSquares(int[][] grid) {
		if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;
		int[][] count = new int[grid.length + 1][grid[0].length + 1];
		int num = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				int row = i + 1, col = j + 1;
				if (grid[i][j] == 0) {
					count[row][col] = 0;
				} else {
					count[row][col] = Math.min(count[row-1][col-1], Math.min(count[row-1][col], count[row][col-1])) + 1;
				}
				num += count[row][col];
			}
		}
		
		return num;
	}
}
```
