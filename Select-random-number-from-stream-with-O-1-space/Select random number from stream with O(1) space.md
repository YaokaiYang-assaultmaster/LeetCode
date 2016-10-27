## Slect a random number from stream with O(1) space. 

*Given a stream of numbers, generate a random number from the stream, you are allowed to use only O(1) space, 
and the input is in the form of stream, so can't store the previously seen numbers. <br>
So how do we generate a random number from the whole stream such that the probability of picking any number is 1/n with O(1) extra space?*

---

Suppose, for the purpose of simpility, the input data stream is 1,2,3,4,...,n-1,n,n+1. <br>
When the datastream inputs 1, rand() function would generate 1.<br>
When the datastream inputs 2, rand() function should generate 1 or 2 with equal possibility 1/2.<br>
When the datastream inputs through n, rand() function should generate 1 to n with equal possibility 1/n.<br>

If we are allowed to use O(n) space, we could simple store all the numbers previously appeared, and use a rand() function to generate a random index, and return the number at this random index. 

However, we are interested in solveing this problem in O(1) space. The algorithm works like following:

1. Keep a variable to store the value that is going to be returned currently. (name it value)
2. Keep a variable to store the total number of numbers we have seen in the datastream. (name it count)
3. Then let t = rand(count) every round. 
   - if t == 0, value = current input value of datastream.
   - if t != 0, value won't be changed. 

This algorithm would give each number in the data stream a equal possibility to be returned. The proof is showed as following:

> **Prove**<br>
> Base case:<br>
> When only 1 number is received from the datastream, than its possibility is 1/1 = 1.<br>
> Induction:<br>
> Assume for i number received, the possibility of generate each number is 1/i. <br>
> Then for the (i+1)th number, since this new number will only be returned when rand(count) == 0, whose possibility is 1/(i + 1). <br>
> And the previous number's possibility to be returned is (1/i) * i/(i + 1) = 1/(i + 1). 

Thus we show thta for each number, its possibility to be returned is 1/n for n numbers in total.<br> 
Yet another thought of this could be:<br>
In order for a number i to showed up until n numbers received, in (i+1) through n steps rand(count) must be non-zero, whose possibility is: P = (1/(i)) * (i/(i + 1)) * ((i + 1)/(i + 2)) * ... * ((n - 1)/ n) = 1/n. This also confirms the correctness of this algorithm. 



