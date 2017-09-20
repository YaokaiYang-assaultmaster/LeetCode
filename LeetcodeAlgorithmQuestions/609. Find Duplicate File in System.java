public class Solution {
    /**
     * Considering we are looking for redundant file_contents and their corresponding path, it would be natural
        to use a HashMap to stores the relationship while using the file_content as the key.
     * Thus the problem is actually a string processing question, that is, to separate the file content and file
        path from the original input Sting array.
     * The time complexity is O(n) where n is the number of files in the paths since we have to go through every
        file_content to check for redundant.
     * The space complexity is O(n) as well since we have to store every {file_content, path} pair in the HashMap.
     */
    public List<List<String>> findDuplicate(String[] paths) {
        List<List<String>> results = new ArrayList<>();
        if (paths == null || paths.length == 0) {
            return results;
        }
        
        Map<String, List<String>> contents = new HashMap<>();
        for (String path : paths) {
            // Get the [file_path, file_names(content)]
            String[] FilePath = path.split(" ");
            for (int i = 1; i < FilePath.length; i++) {
                // Get the [file_name, file_content]
                String[] FileContent = FilePath[i].split("\\(");
                String content = FileContent[1].substring(0, FileContent[1].length());
                if (!contents.containsKey(content)) {
                    contents.put(content, new ArrayList<String>());
                }
                // Reconstruct the full path including file_name
                String FullPath = FilePath[0] + "/" + FileContent[0];
                contents.get(content).add(FullPath);
            }
        }
        for (Map.Entry<String, List<String>> entry : contents.entrySet()) {
            // Get every full file path where there is more than 1 file with the same file_content
            if (entry.getValue().size() > 1) {
                results.add(entry.getValue());
            }
        }
        
        return results;
    }
}
