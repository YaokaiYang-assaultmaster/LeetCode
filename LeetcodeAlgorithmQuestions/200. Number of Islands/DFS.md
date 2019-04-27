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

# Solution3: Union find

If we look through the question, it could actually be a dynamic connectivity problem. With every `1` we have, it could be connected to other `1`s that is horizontally or vertically adjacent to it. So each island here is actually a connected graph.

Hence we can effectively use union find algorithm to find all the connected subgraphs here. Also we need to keep in mind that since it is a `m*n` grid, we only need to move to 1 cell left and 1 cell down for each `1` we meeti during the iteration.  

Here we utilized _Weighted Union with Quick Find_ algorithm.  

## Time complexity:

`O(m*n)` where `m` and `n` is the number of rows and columns we have.  

## Space complexity:

`O(m*n)`

```java
class Solution {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int row = grid.length;
        int col = grid[0].length;
        int[] id = new int[row * col];
        int[] weight = new int[row * col];
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                id[i * col + j] = grid[i][j] == '1' ? i * col + j : -1;
                weight[i * col + j] = 1;
            }
        }
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == '0') continue;
                if (j + 1 < col && grid[i][j + 1] == '1') {
                    union(i * col + j, i * col + j + 1, id, weight);
                }
                
                if (i + 1 < row && grid[i + 1][j] == '1') {
                    union(i * col + j, (i + 1) * col + j, id, weight);
                }
            }
        }
        
        int ret = 0;
        for (int i = 0; i < id.length; i++) {
            if (id[i] == i) ret++;
        }
        
        return ret;
    }
    
    private void union(int i, int j, int[] id, int[] weight) {
        int rootI = getRoot(i, id);
        int rootJ = getRoot(j, id);
        if (rootI == rootJ) return;
        
        if (weight[rootI] < weight[rootJ]) {
            id[rootI] = id[rootJ];
            weight[rootJ] += weight[rootI];
        } else {
            id[rootJ] = id[rootI];
            weight[rootI] += weight[rootJ];
        }
    }
    
    private int getRoot(int i, int[] id) {
        if (id[i] != i) {
            id[i] = id[id[i]];
            i = id[i];
        }
        
        return i;
    }
}
```

# Solution4: BFS

Solve 2-dimension problem with 1-dimension index. index = i * n + j
Using a Queue

## Time complexity

`O(m*n)` where `m` and `n` is the number of rows and columns in the grid

## Space complexity

`O(m*n)`

```java
public class Solution {
    public int numIslands(char[][] grid) {
         if (grid == null) return 0;
         if (grid.length == 0 || grid[0].length == 0) return 0;
         
         int count = 0;
         int row = grid.length, col = grid[0].length;
         for (int i = 0; i < row; i++) {
             for (int j = 0; j < col; j++) {
                 if (grid[i][j] == '1') {
                     removeIslands(grid, i, j, row, col);
                     count++;
                 }
             }
         }
         
         return count;
    }
    
    private void removeIslands(char[][] grid, int x, int y, int row, int col) {
        grid[x][y] = '0';
        Queue<Integer> queue = new LinkedList<>();
        queue.add(x * col + y);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int curX = cur / col;
            int curY = cur % col;
            
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (isValid(i, j, curX, curY, row, col) && grid[curX + i][curY + j] == '1') {
                        queue.add((curX + i) * col + curY + j);
                        grid[curX + i][curY + j] = '0';
                    }
                }
            }
        }
    }
    
    private boolean isValid(int di, int dj, int x, int y, int row, int col) {
        return (Math.abs(di) != Math.abs(dj) && x + di >= 0 && x + di < row && y + dj >= 0 && y + dj < col);
    }
}

```
