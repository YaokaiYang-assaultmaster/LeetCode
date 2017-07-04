public class LogSystem {
    /**
     * Straightforward solution. 
     * store the logs in an ArrayList, and traverse all of them to get the logs within the given retrieving scope. 
     * The comparison can be done by using String.compareTo method.
     * O(1) put, O(n) retrieve. 
     * 
     * However, there could be more efficient but complex ways to do this question. 
     * 1. If there is a lot of put but only a small number of retrieve, we could optimize based on put. 
     *  That is, put operation put new logs directly at the end of the file. And also use lazy sorting, that is,
     *  each time before a retrieving operation, check if there have been any put operation beforehand (probably using a boolean
     *  variable to record). If so, First sort, then retrieve. 
     *  The retrieve process could be done in a binary search way. (looking for start and end in the log ArrayList).
     *  And the sorting can be achieved by override comparator in Collections.sort(). 
     *  
     * Time? The put time is still O(1). The get time is amortized O(logn).
     *
     * 2. If there is a log of retrieve but a small number of put, using binary search to put the log record in the appropriate
     *  Appropriate place. For retrieve, since it's already sorted, just using binary search to retrieve. 
     * Time? O(logn) put, O(logn) retrieve. 
     */
    static Map<String, Integer> granularity = new HashMap<>();
    static {
        granularity.put("Year", 4);
        granularity.put("Month", 7);
        granularity.put("Day", 10);
        granularity.put("Hour", 13);
        granularity.put("Minute", 16);
        granularity.put("Second", 19);
    }
    
    ArrayList<String[]> logRecords;
    public LogSystem() {
        this.logRecords = new ArrayList<>();
    }
    
    public void put(int id, String timestamp) {
        this.logRecords.add(new String[]{String.valueOf(id), timestamp});
    }
    
    public List<Integer> retrieve(String s, String e, String gra) {
        ArrayList<Integer> results = new ArrayList<>();
        int index = granularity.get(gra);
        for (String[] logRecord : logRecords) {
            if (logRecord[1].substring(0, index).compareTo(s.substring(0, index)) >= 0 &&
               logRecord[1].substring(0, index).compareTo(e.substring(0, index)) <= 0) {
                results.add(Integer.parseInt(logRecord[0]));
            }
        }
        
        return results;
    }
}

/**
 * Your LogSystem object will be instantiated and called as such:
 * LogSystem obj = new LogSystem();
 * obj.put(id,timestamp);
 * List<Integer> param_2 = obj.retrieve(s,e,gra);
 */
