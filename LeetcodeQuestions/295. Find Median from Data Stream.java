public class MedianFinder {
    /**
     * O(n) space, O(1)(get) + O(nlogn)(sort the array before getting median) time solution is straight forward
     * Just stores all the values into an array and sort the whole array before finding the medium
     * Or we can always keep the array sorted by inserting the number at the right place.
     * This method is good when the number of getMedians is greater than insertion. 
     */
    
    /**
     * Obviously we are doing redundant works here. We only want the median but we are sorting the whole array.
     * That includes unnecessary work. 
     * In order to prevent from doing useless work, we have to use some data structure to give us the medians without sorting
     * the whole array. Considering that medians is mainly determined by the middle 2 numbers in the input stream, what we need to know
     * is the MAXMIUM of the smaller half and the MINIMUM of the larger half.
     * Thus we could store the whole input stream into 2 heaps in which each contains half of the stream. And left half should
     * be stored in a max-heap, which allows us to get the maximum of the smaller half, right half should be stored in a 
     * min-heap, which allows us to get the minimum of the greater half. And combines the 2 will give us the median given the
     * size of the input stream (odd or even).
     * In this way, the time of get the median will be O(1) for get, O(logn) for insert. Space is still O(n). 
     * Or specifically the time for insertion is O(5 * logn) in the worst case, the condition is: when the 2 heap is of the same size,
     * we insert a number. Then it takes O(2 * logn) to insert the number, take out the max and repercolate the the max. 
     * Then it takes another O(3 * logn) to insert the number to right, take the min from right, repercolate min of right 
     * and insert the min to left. 
     * Also keep in mind that when the total number of data is odd, we keep the smaller half heap to hold 1 more element than 
     * the greater half.
     */
    int count;
    PriorityQueue<Integer> rightHalf;
    // Use a custom comparator to construct a maxHeap.
    PriorityQueue<Integer> leftHalf;
    
    public MedianFinder() {
        this.rightHalf = new PriorityQueue<Integer>();
        // Use a custom comparator to construct a maxHeap.
        this.leftHalf = new PriorityQueue<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b.compareTo(a);
            }
        });
        this.count = 0;
    }
    
    public void addNum(int num) {
        if (this.count == 0) { // Both heap is empty, add number to left
            this.leftHalf.offer(num);
        } else {
            // Always add to leftHalf, then take the maximum from leftHalf and add it to rightHalf.
            this.leftHalf.offer(num);
            this.rightHalf.offer(this.leftHalf.poll());
            
            // This can leads to rightHalf larger than leftHalf.
            // If rightHalf contains more element than left half, rebalance the heaps.
            if (this.rightHalf.size() > this.leftHalf.size()) {
                this.leftHalf.offer(this.rightHalf.poll());
            }
        }
        this.count++;
    }
    
    public double findMedian() {
        if ((this.count & 1) != 0) { // odd number of elements
            return this.leftHalf.peek();
        } else {
            return (Double.valueOf(this.leftHalf.peek()) + Double.valueOf(this.rightHalf.peek())) / 2;
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
