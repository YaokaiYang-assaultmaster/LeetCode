/* The knows API is defined in the parent class Relation.
      boolean knows(int a, int b); */

public class Solution extends Relation {
    /**
     * O(n) solution.
     * First iteration to find out a possible candidate (only 1 candidates may exists). 
     * Following 1 or 2 rounds to find out if this candidate is a real celebrity or not.
     * The key to understand the first loop is that we can view this process to be a process of finding "maximum".
     * If a knows b, then b is greater than a. 
     * Thus only the one who is greater than everyone else can be a possible candidate.
     * Next we just verify if this candidate truly meet the criteria.
     */
    public int findCelebrity(int n) {
        int candidate = 0;
        for (int i = 1; i < n; i++) {
            if (knows(candidate, i)) {
                candidate = i;
            }
        }
        
        // find out if this candidate knows anyone prior to it.
        // only need to check from [0, candidate) because any one behind candidate has already been
        // checked in the first loop.
        for (int i = 0; i < candidate; i++) {
            if (knows(candidate, i)) {
                return -1;
            }
        }
        
        // check if everyone knows the candidate
        for (int i = 0; i < n; i++) {
            if (!knows(i, candidate)) {
                return -1;
            }
        }
        
        return candidate;
    }
}
