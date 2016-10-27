## A conclusion for Generate Rand(n) from Rand(m) where n > m. 

1. Generate Rand(3) from Rand(2)
   Rand(3) can generate 0,1,2 with equal possibility 1/3, Rand(2) can generate 0,1 with equal possibility 1/2. How could we generate Rand(3) using Rand(2)?
   Note taht Rand(2) + Rand(2) can generate 0,1,2, though, with unequal possibility. As showed following.<br>
   ![image of Rand(2) and Rand(3)](https://github.com/YaokaiYang-assaultmaster/LeetCode/blob/master/Generate-Rand-n-from-Rand-m/Rand(2)%26Rand(3).png)<br>
   So how could we generate 0,1,2 with equal possibility in this case? noticed that we could achieve this by dropping one of the result in sum of Rand(2). 
   So we drop the result(1,1), which means that if we encounter (1,1) we simply repeat this process in order to get another result. And then when we encounter(0,0), we return 0, (1,0) would return 1 and (0,1) would return 2. By doing so, we achieve the result of generating 0, 1, 2 with equal possibility 1/3.
   
2. Generate Rand(5) with Rand(3)
   Rand(3) + Rand(3) generate a result of 0 to 4, with unequal possibility, showed as following:<br>
   ![image of Rand(3) and Rand(5)](https://github.com/YaokaiYang-assaultmaster/LeetCode/blob/master/Generate-Rand-n-from-Rand-m/Rand(3)%26Rand(5).png)<br>
   Rand(3) * Rand(3) is even worse since it can only generate 0, 1, 2, 4 with unequal possibility. So we will still use Rand(3) + rand(3). 
   So how should we deal with it?
   There is a generalized method showed as following:<br>
   ![general method](https://github.com/YaokaiYang-assaultmaster/LeetCode/blob/master/Generate-Rand-n-from-Rand-m/General%20method.png)<br>
   Each time we got some unqeual possibility result, we simple keep the number of results we need and then drop the remainning ones, which means that if we encounter with some combination that is not needed, we repeat the random process again in order to get a valid result. 
   Thus we can solve with this kind of problem easily. 
