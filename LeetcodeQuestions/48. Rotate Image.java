public class Solution {
    /** 
     * Iterative solution. rotate contour of each square layer by layer 
     * O(1) space, O(n^2) solution, only applies to square.
     */
    public void rotate(int[][] matrix) {     
        // Empty matrix doesn't need to be rotated
        if (matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        
        int round = matrix.length / 2; // total rounds we need to rotate the matrix
        int len = matrix.length - 1;
        for (int i = 0; i < round; i++) {
            for (int j = i; j < len - i; j++) {
                // In each round, rotate the contour of the current square.
                // Note we don't have to rotate the last point of each line.
                // Since it's already been rotated as the beginning of next line
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[len - j][i];
                matrix[len - j][i] = matrix[len - i][len - j];
                matrix[len - i][len - j] = matrix[j][len - i];
                matrix[j][len - i] = tmp;
            }
        }
        return;
    }
    
    /**
     * Recursive solution
     * O(1) space without recursion stack. O(n^2) time since we have to iterate through every point of the matrix
     * The recursion stack would be O(matrix.length / 2) level.
     */
    public void rotate(int[][] matrix) {
        rotateHelper(matrix, 0);
        return;
    }
    
    private void rotateHelper(int[][] matrix, int round) {
        int len = matrix.length;
        if (round == len / 2) {
            return;
        }
        for (int i = round; i < len - round - 1; i++) {
            int tmp = matrix[round][i];
            matrix[round][i] = matrix[len - 1 - i][round];
            matrix[len - 1 - i][round] = matrix[len - 1 - round][len - 1 - i];
            matrix[len - 1 - round][len - 1 - i] = matrix[i][len - 1 - round];
            matrix[i][len - 1 - round] = tmp;
        }
        rotateHelper(matrix, round + 1);
    }
    
    /**
     * Not rotate in place, need a new array of the same size of the original one. 
     * O(n^2) time and O(n^2) space
     */
    public void rotate(int[][] matrix) {

        int row = matrix.length;
        int column = matrix[0].length;
        int[][] rotated = new int[column][row];
        for (int i = 0; i < column; i++){
            for (int j = row - 1; j>=0; j--){
                rotated[i][row-1-j] = matrix[j][i];
            }
        }
        for (int i = 0; i < row; i ++){
            for (int j = 0; j < column; j ++){
                matrix[i][j] = rotated[i][j];
                //System.out.println(matrix[i][j])
            }
        }
    }
}
