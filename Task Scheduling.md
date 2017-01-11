> Given a sequence of tasks, e.g. A, B, C (3 different tasks) and a cold time, e.g. 2, which means that you need to wait for that much time to start next __same__ task. Now:  
> Given a input String of tasks, s, output the best task-finishing sequence.   
> e.g. input: AAABBB, 2  
> output: AB_AB_AB ("_" means do nothing and wait)  

- Analysis:  
	We want to find out the best task-finishing sequence of the input tasks. Since there is colddown time for each same tasks, it would be reasonable to let the tasks that appears more frequently to be executed early. Thus we may need to sort the tasks based on their frequency.   
	Then we need to execute them based on frequency in a way such that high frequent tasks are executed early. After one execution(i.e. all tasks have been executed once), we have to update the frequencies of each of them. And now we have to check if the colddown time has passed. If passed, we can execute another round. Otherwise, we have to wait(i.e. put "_" in the result).  
	For the sorting process, a map sorted based on value is suitable. A list of wrapper class of (task, frequency) pairs sorted based on frequency could also be used.  
	Then we have to simply walk through the tree or the list to schedule tasks and put them in the result.   
	The time complexity of sorting is `O(nlogn)`. And later walk-through takes `O(n)` time. Thus the total time if `O(nlogn)`. The space complexity if `O(n)`. `n` is the number of tasks. 

At first I wrote a solution showed as following with treeMap which is sorted based on values. However, this code has the wired bug of losting tasks with the same frequency. After a check of treeMap, I found out that treeMap does not allow for elements with the same value based on its comparator. Thus our treeMap would drop elements which is equal based on our self-defined comparator, i.e. compare() returns 0. This code works fine with a sequence of tasks with different frequencies respectively. 

```
public class Solution {
	
	class ValueComparator<Character> implements Comparator<Character> {
		
		Map<Character, Integer> base;
		
		public ValueComparator(Map<Character, Integer> base) {
			this.base = base;
		}
		@Override
		public int compare(Character o1, Character o2) {
			return base.get(o2) - base.get(o1);
		}
		
	}
	public String taskSchedule(String tasks, int coldTime) {
		if (tasks == null || tasks.length() == 0) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		ValueComparator vc = new ValueComparator(map);
		Map<Character, Integer> sortedMap = new TreeMap<Character, Integer>(vc);

		for (int i = 0; i < tasks.length(); i++) {
			if (!map.containsKey(tasks.charAt(i))) {
				map.put(tasks.charAt(i), 1);
			} else {
				map.put(tasks.charAt(i), map.get(tasks.charAt(i)) + 1);
			}
		}
		
		for (Map.Entry<Character, Integer> e : map.entrySet()) {
			sortedMap.put(e.getKey(), e.getValue());
		}
		
		System.out.println(sortedMap);

		int totalCount = 0;
		int currLen = 0;
		while (totalCount < tasks.length()) {
			for (char ele : sortedMap.keySet()) {
				if (sortedMap.get(ele) > 0) {
					sb.append(String.valueOf(ele));
					sortedMap.put(ele, sortedMap.get(ele) - 1);
					totalCount++;
					currLen++;
				}
			}

			if (totalCount == tasks.length()) {
				break;
			}

			while (currLen <= coldTime) {
				sb.append("_");
				currLen++;
			}

			currLen = 0;
		}

		return sb.toString();
	}
}
```						 
						 
Thus we have to think of another data structure to solve it. A list sorted based on frequencies works. The code is showed as following. 
						 
```

```
	
