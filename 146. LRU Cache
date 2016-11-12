public class LRUCache {
    
    
    /**
     * Use a Doulbe LinkedList to store the key-value pairs. 
     * Keep pseudo old and recent pointers pointing at the oldest poisition and most recent poisition, which makes the insertion much easier. And we don't have to check for boundary conditions (Null nodes) during update.  
     * Use a HashMap to keep track of the key and its corresponding node in the linkedlist. 
     * All operations are in O(1) time.
     */
    private class DLinkedNode {
        int key;
        int value;
        DLinkedNode pre;
        DLinkedNode next;
    }
    
    private int capacity;
    private int count;
    private DLinkedNode old;
    private DLinkedNode recent;
    private Map<Integer, DLinkedNode> cache;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.count = 0;
        this.cache = new HashMap<>();
        
        //Use dummy old and recent to deal with boundary condition
        old = new DLinkedNode();
        recent = new DLinkedNode();
        old.pre = null;
        recent.next = null;
        old.next = recent;
        recent.pre = old;
    }
    
    public int get(int key) {
        DLinkedNode curr = cache.get(key);
        if (curr == null) {
            return -1;
        } else {
            this.moveToHead(curr);
            return curr.value;
        }
    }
    
    public void set(int key, int value) {
        DLinkedNode curr = cache.get(key);
        if (curr == null) {
            curr = new DLinkedNode();
            
            curr.key = key;
            curr.value = value;
            cache.put(key, curr);
            addNode(curr);
            
            count++;
            
            if (count > capacity) {
                DLinkedNode tail = popTail();
                cache.remove(tail.key);
                count--;
            }
        } else {
            curr.value = value;
            moveToHead(curr);
        }
    }
    
    /**
     * Always add the node to the most recent side of the linkedlist.
     */
    private void addNode(DLinkedNode node) {
        recent.pre.next = node;
        node.next = recent;
        node.pre = recent.pre;
        recent.pre = node;
    }
    
    /**
     * Remove an existing node from the linkedlist. 
     */
    private void removeNode(DLinkedNode node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }
    
    /**
     * Move an existing node to the most recent side of the linkedlist. 
     */
    private void moveToHead(DLinkedNode node) {
        this.removeNode(node);
        this.addNode(node);
    }
    
    /**
     * Pop out the least recent node in the linkedlist.
     */
    private DLinkedNode popTail() {
        DLinkedNode tail = old.next;
        old.next = tail.next;
        tail.next.pre = old;
        
        return tail;
    }
    
}
