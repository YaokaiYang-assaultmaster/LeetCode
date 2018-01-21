/*
Since we only want the biggest number among those numbers that only appears once, it would be obvious that we need to add a COUNT(num) column in order to perform the appearance filtering. 
Then we only have to select max(num) from those numbers have Count(num) as 1. 
*/
SELECT MAX(table1.num) AS num FROM (
    SELECT COUNT(num) AS counts, num FROM number GROUP BY num
) table1
    WHERE table1.counts=1;
