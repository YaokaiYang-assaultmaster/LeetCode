> Given a string which contains only lowercase letters, remove duplicate letters so that every letter appear once and only once. You must make sure your result is sorted in lexicographical order.
> e.g. Given `"bcabc"` Return `"abc"`.

- Analysis:

	In order to return a sorted String, we could use data structures like min-Heap. And we could use a HashSet to store existed letters. However, considered there is only lower case letters in the String, we could do it greedily by finding the smallest letters each time and append it to the result string. After our smallest letter is 'z' or there is no smaller letters based on current result, we break and return. 
	
	`O(26n)` = `O(n)` time since there is at most 26 letters. `O(1)` space since we only keep 1 variable. 
	
The code implemented in Java is showed as following. 

```
public class Solution {
    public String removeDuplicateLetters(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        
        char[] ch = s.toCharArray();
        char min = 'a' - 1;
        StringBuilder sb = new StringBuilder();
        while (true) {
            char currMin = 'z' + 1;
            for (int i = 0; i < ch.length; i++) {
                if(ch[i] > min && ch[i] < currMin) {
                    currMin = ch[i];
                }
            }
            
            if (currMin != 'z' + 1) {
                sb.append(currMin);
            }
            
            if (currMin == 'z' || currMin == 'z' + 1) {
                break;
            }
            min = currMin;
        }
        
        return sb.toString();
    }
}
```
