> Given a unknown number of inputs `(stockName, shares)`, update the shares if already exists or store the shares if not, and output the top K stock name with most shares. If there are more than one stocks has the same number of shares, rank them based on alphabatical order.     
> e.g. For input `"MSFT", 100`, `"AAPL, 200`, `topK(1)` should return `["AAPL"]`. After two more inputs `"MSFT", 300`, `"GOOG", 400`, `topK(2)` should return `["GOOG", "MSFT"]`.  

This question is similar to [347. Top K Frequent Elements](https://github.com/YaokaiYang-assaultmaster/LeetCode/blob/master/LeetcodeAlgorithmQuestions/347.%20Top%20K%20Frequent%20Elements.md) and [692. Top K Frequent Words](https://github.com/YaokaiYang-assaultmaster/LeetCode/blob/master/LeetcodeAlgorithmQuestions/692.%20Top%20K%20Frequent%20Words.md). 

The main difference is that we need to update the shares on the fly and construct the top K list if needed. We borrow the similar idea from [460. LFU Cache](https://github.com/YaokaiYang-assaultmaster/LeetCode/blob/master/LeetcodeAlgorithmQuestions/460.%20LFU%20Cache.md), in which we used a TreeMap to sort based on frequencies. In this question we used a TreeSet to sort based first on the number of shares and then on the name of the stock. In addition to the TreeSet, we also used a HashMap to maintain the link between the stock name and the `[name, share]` pair stored in the TreeSet in order to update it if needed.   

Time complexity: `O(logn)` for `put`, `O(k)` for `topK`. `n` is the number of different stock names we encountered so far.  

Space complexity: `O(n)`.  

```Java
public class StreamingTopK {
	class Pair implements Comparable<Pair> {
		String name;
		Integer shares;
		public Pair (String name, Integer shares) {
			this.name = name;
			this.shares = shares;
		}
		public int compareTo(Pair that) {
			if (this.shares.equals(that.shares)) {
				return this.name.compareTo(that.name);
			}
			return -1 * this.shares.compareTo(that.shares);
		}
	}
	HashMap<String, Pair> stocks;
	TreeSet<Pair> tree;
	
	public StreamingTopK() {
		stocks = new HashMap<>();
		tree = new TreeSet();
	}
	
	/**
	 * Store a company with corresponding number of shares. 
	 * @param stock Stock name of the company
	 * @param shares number of shares for this company
	 */
	public void put(String stock, int shares) {
		if (stocks.containsKey(stock)) {
			Pair oldPair = stocks.get(stock);
			tree.remove(oldPair);
		}
		Pair newPair = new Pair(stock, shares);
		stocks.put(stock, newPair);
		tree.add(newPair);
	}
	
	/**
	 * Return the top k company with most shares
	 * @param k Number of company need to be returned
	 * @return List of company names
	 */
	public List<String> topKCompany(int k) {
		Iterator<Pair> it = tree.iterator();
		List<String> ret = new ArrayList<>();
		while (k > 0 && it.hasNext()) {
			Pair pair = it.next();
			ret.add(pair.name);
			k--;
		}
		
		return ret;
	}
}
```
