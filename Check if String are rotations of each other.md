Check if one of the two Strings can be got by rotating the ohter one. 
For example, if `s1 = "stackoverflow"`, then the following are some of its rotated versions:<br>
`"tackoverflows"`<br>
`"ackoverflowst"`<br>
while `"stackoverflwo"` is __not__ a rotated version. 

For this question, the brute-force solution would be generated every possible rotation of s1 and compare the s2 with them. This would be 
`O(n^2)` time and `O(n^2)` space suppose the length of s1 and s2 is n. Since we generated n rotations of s1, each of the length n and Stirng is 
immutable, the space is `n*n`. And generate each rotation need `O(n)` time, thus the time for generating n String is `O(n^2)`. 
However, we do a lot of unnecessary work here. We only need 1 rotation of s1 and we actually generate n rotation of s1. Thus we can optimize
this algorithm by cutting out unnecessary work. So we may consider to match s2 in s1, which remove the work of generating rotations of s1.<br>
Based on this observation, we may consider using 2 pointers in s1 to label the start and end point of matching. Since we want to check if s2 is a
rotation of s1, we may consider duplicate s1 to generate `s1 + s1`. This approach give us the convenience of don't have to consider the case that 
when the pointer reaches the end of s1, it must go back to the start of s1. <br>
And yet another observation is that if s2 is actually a rotation of s1, it must be the specific rotation that start at the same character 
at the beginning s2. Using above example, we can compare `"ackoverflowst"` with `"stackoverflowstackoverflow"` from character a 
in `"stackoverflowstackoverflow"`. 
In this way we can compare s1 and s2 in linear time. 
We can even use algorithm like KMP to compare them efficiently in O(n) time. 
