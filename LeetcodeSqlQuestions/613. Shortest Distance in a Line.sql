/*
1. Self join. 
We want to get the minimum distance between 2 different x values. Thus we could first perform a self join of `point` table based on different x values. Hence we get all the different possible combinations of x pairs in `point`. 
After that just get the minimum absolute difference between 2 x values. 
*/
SELECT MIN(ABS(P1.x - P2.x)) AS shortest FROM point AS P1 join point AS P2 WHERE P1.x <> P2.x;

/*
2. Self join.
The above statement has redundant calculations because we get both the positive differences and negative differences and then use absolute function to get the result. 
However, this can be avoided by only join based on P1.x > P2.x. 
*/
SELECT MIN(P1.x - P2.x) AS shortest FROM point P1 join point P2 WHERE P1.x > P2.x;

/*
Solution for the follow-up question. 
Follow-up: What if all these points have an id and are arranged from the left most to the right most of x axis?
*/
SELECT MIN(P1.x - P2.x) AS shortest FROM point P1 join point P2 WHERE P1.id = P2.id + 1 AND P1.id > 1;
