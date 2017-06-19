public class Solution {
    /** 
     * Considering the minimum conceptive time of this question. 
     * It is obvious that we cannot solve this question within less than O(n^2) time anyway. 
     * Since we have to find out every single match of String in dict and s. 
     * Only after that can we decide which are the overlapping and consecutive bold tags. 
     * Thus the Solution would be straightforward. 
     * 1. Find out the substring in s that exists in dict. 
     * 2. Figure out some way to store this bolding info.
     * 3. Merge the bold interval and done. 
     * 
     * Considering we need to merge overlapping and consecutive bold tags, it would be inefficient to add in bold tags and merge afterwards. 
     * Using a boolean array to store the bold stratus of each character would be the therapy.
     * After finding all substring and marked them as bold, traverse the bold array and create a new String based on the status.
     * Directly change the original String would be a headache because of each inserted bold tag would result in an index change of every character after it. 
     * Thus just create a new one. 
     * 
     * An minor optimization is to traverse s first and use a HashMap to store the information of characters and corresponding indexes of them. 
     * This could make it easier to decide not-substring conditions and where to begin the search. 
     * 
     * Showed as following.
     */
    public String addBoldTag(String s, String[] dict) {
        // Get rid of invalid inputs
        if (s == null || dict == null) return "";
        if (s.length() == 0 || dict.length == 0) return s;
        
        StringBuilder str = new StringBuilder(s);
        Map<Character, List<Integer>> charMap = new HashMap<>();
        boolean[] bold = new boolean[str.length()];
        
        // Prepossessing s in order to make the substring finding process efficient
        for (int i = 0; i < str.length(); i++) {
            if (charMap.get(str.charAt(i)) == null) {
                charMap.put(str.charAt(i), new ArrayList<>());
            }
            charMap.get(str.charAt(i)).add(i);
        }
        
        for (String substr : dict) {
            findAndBoldSubstring(str, charMap, substr, bold);
        }
        
        return mergeBoldTags(str, bold);
    }
    
    private void findAndBoldSubstring(StringBuilder str, Map<Character, List<Integer>> charMap, String substr, boolean[] bold) {
        int subPointer = 0;
        int indexPointer = 0;
        List<Integer> indexes = charMap.get(substr.charAt(subPointer));
        
        if (indexes == null) return;
        
        while (indexPointer < indexes.size()) {
            int strPointer = indexes.get(indexPointer);
            while (subPointer < substr.length() && strPointer < str.length()) {
                // Notice that if we use if (substr.charAt(subPointer++) != str.charAt(strPointer++)) here,
                // the result would be wrong.
                // The conner case would be the substr and str have the same character until the very last one. 
                // At the time we break, subPointer is plused by 1 and equal to substr.length(), thus makes the 
                // following if statement treat the substring as a valid match. 
                if (substr.charAt(subPointer) != str.charAt(strPointer)) {
                    break;
                } else {
                    subPointer++;
                    strPointer++;
                }
            }
            
            if (subPointer == substr.length()) {
                int start = indexes.get(indexPointer);
                int end = start + substr.length() - 1;
                for (int i = start; i <= end; i++) {
                    bold[i] = true;
                }
            }
            
            subPointer = 0;
            indexPointer++;
        }
    }
    
    private String mergeBoldTags(StringBuilder str, boolean[] bold) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (!bold[i]) {
                result.append(str.charAt(i));
            } else {
                int j = i;
                while (j < str.length() && bold[j]) j++;
                result.append("<b>");
                result.append(str.substring(i, j));
                result.append("</b>");
                i = j - 1;
            }
        }
        
        return result.toString();
    }
}
