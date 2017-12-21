> Create a one dimensional smash game based on numbers. Given the smash level, which is the number of consecutive elements that need to be smashed once encountered (if the number of consecutive elements is greater than or equal to this level), and a list of numbers as input. Return the list of numbers after smashing as the output.     
> E.g. for smash level 3, input `[1,2,2,2,1,1]` will return `[]`, input `[1,2,2,2,1,3,3,3,4]` will return `[1,1,4]`. 

At a first glacnce, this question seem to be easy. We can simply use an Array or a LinkedList and mimic the process of smashing. Once the number of consecutive elements is greater than or equal to the smashLeve, we delete those elements and continue checking. By continue checking, I mean that we need to also put the start position of checking back by `smashLevel - 1` since this is the furthest position possible to construct another sequence need to be smashed based on the new next position after deleting the current sequence. 

However, these methods have several pain points:

1. For LinkedList, the stopping point is hard to tell.    
2. For arrayList, the stopping point is also hard to tell because we are deleting elements constantly. 
3. We need to adjust the current start checking position constantly, which requires complex and trivial contolling logic that is hard to implement.    
4. The process of adjust the position and deleting elements is uncertain because we don't know how many elements are the same, should they exceeds smashLevel. 

However, take into consideration that we are checking backwards, a stack would be much more convenient. We can always check the previous elements by peek the top elements of the stack. And once we want to delete a sequence, simply pop the top. And the new top would naturally be the elements of the previous position.     
Meanwhile, inorder to keep track the number of elements with the same value with in the input, we keep another stack storing the `count` information of each elements. After putting consecutive elements with the same value into the stack all at once, we could decide if we should delete it or not by simply checking the corresponding `count` in the count stack. 

Implementation in Java as following. 

Time complexity: `O(n)` since we have to check each element once. `n` is the number of input elements. 

Space complexity: `O(n)` since we use 2 stack to store information. 

```
public class OneDimensionalSmash {
	/**
	 * where the number of consecutive elements with the same value is greater than or equal to
	 * smashLevel, they will be smashed. 
	 */
	int smashLevel;
	
	OneDimensionalSmash(int level) {
		this.smashLevel = level;
	}
	
	public List<Integer> smash(int[] input) {
		Deque<Integer> deque1 = new ArrayDeque<>();
		Deque<Integer> deque2 = new ArrayDeque<>();
		int i = 0;
		while (i < input.length) {
			if (deque1.isEmpty() || input[i] != deque1.peek()) {
				deque1.push(input[i]);
				deque2.push(1);
				i++;
			} else { // !deque.isEmpty() && input[i] == deque1.peek()
				while (i < input.length && deque1.peek() == input[i]) {
					int count = deque2.pop();
					deque2.push(count + 1);
					i++;
				}
				if (deque2.peek() >= this.smashLevel) {
					deque2.pop();
					deque1.pop();
				}
			}
		}
		
		List<Integer> ret = new ArrayList<>();
		while (!deque1.isEmpty()) {
			int currNumber = deque1.pop();
			int currCount = deque2.pop();
			for (int j = 0; j < currCount; j++) {
				ret.add(currNumber);
			}
		}
		Collections.reverse(ret);
		return ret;
	}
}

```
