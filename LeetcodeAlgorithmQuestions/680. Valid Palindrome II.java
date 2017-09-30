class Solution {
    public boolean validPalindrome(String s) {
        if (s.isEmpty() || s.length() == 0) {
            return true;
        }
        
        char[] charS = s.toCharArray();
        int left = 0;
        int right = charS.length - 1;
        
        return validationHelper(charS, left, right, false);
    }
    
    private boolean validationHelper(char[] s, int left, int right, boolean deleteFlag) {
        while (left < right) {
            if (s[left] != s[right] && !deleteFlag) {
                return validationHelper(s, left + 1, right, true) || validationHelper(s, left, right - 1, true);
            } else if (s[left] != s[right] && deleteFlag) {
                return false;
            } else {
                left++;
                right--;
            }
        }
        
        return true;
    }
}
