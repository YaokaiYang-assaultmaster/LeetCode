public KeyNotFound extends Exception {
    public KeyNotFound(String message) {
        super(message);
    }
}

public class Trie {
    class TrieNode {
        char curr;
        TrieNode[] children;
        private boolean isEnd;
        String data; 
        private int count;
        TrieNode (char curr, boolean isEnd, String data) {
            // Cast all the chars to lower case
            curr = Character.toLowerCase(curr);
            this.curr = curr;
            this.isEnd = isEnd;
            this.data = data;
            this.children = new TrieNode[26];
            this.count = 1;
        }
        
        void setEnd(boolean end) {
            this.isEnd = end;
        }
        
        void increaseCount() {
            this.count++;
        }
        
        int getCount() {
            return this.count;
        }
        
        void decreaseCount() {
            this.count--;
        }
    }
    
    private TrieNode root;
    
    public Trie() {
        /** Initialize the Trie. */
        this.root = new TrieNode(' ', false, "");
    }
    
    public void insert(String word, String data) {
        /** Inserts a word and corresponding data into the trie. */
        char[] chars = word.toCharArray();
        TrieNode currPos = this.root;
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i] - 'a';
            // If a node corresponding to current substring does not exists, create one.
            if (currPos.children[index] == null) {
                TrieNode newNode = new TrieNode(chars[i], false, ""); 
                currPos.children[index] = newNode;
            }
            // If this is the end of the word, store the data in to the Trie and set end.
            if (i == chars.length - 1) {
                currPos.children[index].setEnd(true);
                currPos.children[index].data = data;
            }
            // Move one level down
            currPos = currPos.children[index];
        }
    }
    
    public boolean search(String word) {
        /** Returns if the word is in the Trie. */
        char[] chars = word.toCharArray();
        TrieNode currPos = this.root;
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i] - 'a';
            // If no match find, return false
            if (currPos.children[index] == null) {
                return false;
            }
            if (i == chars.length - 1 && currPos.children[index].isEnd) {
                return true;
            }
            currPos = currPos.children[index];
        }
        return false;
    }
    
    public boolean startsWith(String prefix) {
        /** Returns if there is any word in the Trie that starts with the given prefix. */
        char[] chars = prefix.toCharArray();
        TrieNode currPos = this.root;
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i] - 'a';
            if (currPos.children[index] == null) {
                return false;
            }
            currPos = currPos.children[index];
        }
        return true;
    }
    
    public String getData(String key) throws KeyNotFound {
        /** 
         * Gets the corresponding data for the given key if the key exists in the Trie.
         * Throw KeyNotFound error if no such key exists
         */   
        char[] chars = key.toCharArray();
        TrieNode currPos = this.root;
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i] - 'a';
            // If no match find, return empty string
            if (currPos.children[index] == null) {
                throw new KeyNotFound("Given key does not exists in the Trie!");
            }
            currPos = currPos.children[index];
        }
        if (!currPos.isEnd) {
            throw new KeyNotFound("Given key does not exists in the Trie!");
        }
        return currPos.data;
    }
    
    public boolean setData(String key, String newData) {
        /**
         * Change the data of an existing key.
         * If changed successfully, return true.
         * If the key doesn't exists, return false.
         */
        char[] chars = key.toCharArray();
        TrieNode currPos = this.root;
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i] - 'a';
            // If no match find, cannot store the data, return false
            if (currPos.children[index] == null) {
                return false;
            }
            currPos = currPos.children[index];
        }
        if (!currPos.isEnd) {
            return false;
        }
        currPos.data = newData;
        return true;
    }
    
    public void delete(String word) {
        /**
         * Delete the given word in the Trie if exists
         */
        // If the word doesn't exist in the Trie, should delete anything
        if (!search(word)) {
            return;
        }
        char[] chars = word.toCharArray();
        TrieNode currPos = this.root;
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i] - 'a';
            TrieNode tmp = currPos.children[index];
            if (currPos.children[index].getCount() == 1) {
                currPos.children[index] = null;
            } else {
                currPos.children[index].decreaseCount();
            }
            
            // if a word is deleted but the prefix still exists, delete the data and flip isEnd flag.
            if (i == chars.length - 1) {
            	if (currPos.children[index] != null) {
            		currPos.children[index].data = "";
            		currPos.children[index].setEnd(false);
            	}
            }
            currPos = tmp;
        }
        
    }
}
