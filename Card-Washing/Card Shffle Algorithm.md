## Card Shuffle Algorithm

In card shuffle, we need to guarantee every card has a possibily 1/54 to be in each position. 
Algorithm showed as following:<br>

1. Use an array arr[54] to store all the cards. 
2. From index 0, for every index, there is index + 1 cards already been shuffled. For rand(index + 1)
   1. if index == rand(index + 1), do nothing. 
   2. if index != rand(index + 1), shuffle card at index with card at rand(index + 1). 
3. Repeat this process until index == 53. 
<br>

> Proof:<br>
> Assume there was n cards already been shuffled, now we want to add a new card in to this pile. And our goal is to add all 54 cards into this pile. <br>
> Then for each poisition, this card has a possibility of 1/(n + 1) to be put. Since we are using rand(n + 1). <br>
> FOr each n cards before this new card added, it has a possibility of 1/n to be in each poisition. And its possibility of staying at the same poisition is n/(n + 1). So when a new card is added, its possibility of stay in the original poisition is: P = (n/(n + 1))*1/n = 1/(n + 1).
