# Solution1: DFS graph search with visited array

Simply put, we iterate through the input `grid` and track all connected sub-graphs composed with all `1`s. While searching for connected sub-graph, we could mark all visited cell in the `boolean[][] visited` array to prevent dead loop. Once we find a new sub-graph, we increase the number of islands by 1. 

That gives us the 3 step algorithm:  
1. find an entrance of an island  
2. marked the islands using DFS  
3. counting the number of entrance  

## Time complexity

`O(m * n)` where `m` is the number of rows in the `grid` and `n` is the number of columns in the `grid`. 

## Space complexity

`O(m * n)`. 

```java
class Solution {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        int ret = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    visited[i][j] = true;
                    dfs(grid, i, j, visited);
                    ret++;
                }
            }
        }
        
        return ret;
    }
    
    private void dfs(char[][] grid, int i, int j, boolean[][] visited) {
        if (grid[i][j] == 0) return;
        for (int deltaI = -1; deltaI <= 1; deltaI++) {
            for (int deltaJ = -1; deltaJ <= 1; deltaJ++) {
                if (Math.abs(deltaI) != Math.abs(deltaJ)) {
                    int newI = i + deltaI;
                    int newJ = j + deltaJ;
                    if (newI < 0 || newI >= grid.length || newJ < 0 || newJ >= grid[0].length) continue;
                    if (grid[newI][newJ] == '1' && !visited[newI][newJ]) {
                        visited[newI][newJ] = true;
                        dfs(grid, newI, newJ, visited);
                    }
                }
            }
        }
    }
}
```

# Solution2: DFS without visited array.

Or we could also alter the input to achieve the same effect. We could alter all visited cells to `0`. 

## Time complexity

`O(m * n)`

## Space complexity

`O(m * n)`

```java
public class Solution {
    /**
     * Boundary check. 
     * Since we only want to check 4 direction (vertical and horizontal), we need to make sure di and dj won't be 1 or 0 at the same time. 
     */
    private boolean isValid(int di, int dj, int x, int y, int row, int col) {
        return (Math.abs(di) != Math.abs(dj) && x + di >= 0 && x + di < row && y + dj >= 0 && y + dj < col);
    }
    
    /**
     * If we can change the input, we can mark visited points in place without using the extra boolean array.
     * We marded every '1' point we have visited as '0' and thus marked it. 
     * O(n ^ 2) time and O(1) space without considering the recursion stack
     * The recursion stack would be O(n ^ 2) in the worse case. 
     */
    public int numIslands(char[][] grid) {
        if (grid == null) return 0;
        if (grid.length == 0 || grid[0].length == 0) return 0;
        
        int count = 0;
        int row = grid.length;
        int col = grid[0].length;
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    removeIslands(grid, i, j, row, col);
                }
            }
        }
        
        return count;
    }
    
    private void removeIslands(char[][] grid, int x, int y, int row, int col) {
        grid[x][y] = '0';
        
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isValid(i, j, x, y, row, col) && grid[x + i][y + j] == '1') {
                    removeIslands(grid, x + i, y + j, row, col);
                }
            }
        }
    }
    
    private boolean isValid(int di, int dj, int x, int y, int row, int col) {
        return (Math.abs(di) != Math.abs(dj) && x + di >= 0 && x + di < row && y + dj >= 0 && y + dj < col);
    }
}
```
