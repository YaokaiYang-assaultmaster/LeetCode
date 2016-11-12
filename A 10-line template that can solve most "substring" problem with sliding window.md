In most substring problems, we are given a string and need to find a substring of it which satisfy some restrictions. A general approach is to 
use a HashMap associated with 2 pointers. 
The template is given below:
```Java
public int findSubstring(String s) {
  int[] map = new int[256];
  //could also use hashmap: Map<Character, Integer> hashmap = new HashMap<>();
  int counter; //check whether the substring is valid
  int start = 0, end = 0; //2 pointers, pointing to the start and end of the substring, respectively
  int d; //the length of the substring
  
  for () {/**Initialize the hashmap here*/}
  
  while (end < s.length()) {
    if (map[s.charAt(end++)]-- ?) {/**Modify the counter here*/}
    
    while (/**counter condition*/) {
    
      /**update d here if find minimum*/
      
      //increase start to make the it invalid/valid again
      
      if (map[s.charAt(start++)]++ ?) {/**modify counter here*/}
    }
    
    /**update d here if find maximum*/
  }
  
  return d;
}
```
