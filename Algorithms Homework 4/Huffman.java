// Jigar Swaminarayan
package huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Huffman instances provide reusable Huffman Encoding Maps for
 * compressing and decompressing text corpi with comparable
 * distributions of characters.
 */
public class Huffman {
    
    // -----------------------------------------------
    // Construction
    // -----------------------------------------------

    private HuffNode trieRoot;
    private Map<Character, String> encodingMap;
    
    /**
     * Creates the Huffman Trie and Encoding Map using the character
     * distributions in the given text corpus
     * @param corpus A String representing a message / document corpus
     *        with distributions over characters that are implicitly used
     *        throughout the methods that follow. Note: this corpus ONLY
     *        establishes the Encoding Map; later compressed corpi may
     *        differ.
     */
    Huffman (String corpus) {
        // TODO!
    	PriorityQueue<HuffNode> probabilities = new PriorityQueue<>();
    	HashMap<Character, Integer> frequencies = new HashMap<>();
    	int length = corpus.length();
    	
    	// Step 1: Retrieve frequencies of letters in corpus
    	for (int i = 0; i < length; i++) {
    		int count;
    		if (!frequencies.containsKey(corpus.charAt(i))) {
    			frequencies.put(corpus.charAt(i), 1);
    		} else if (frequencies.containsKey(corpus.charAt(i))) {
    			count = frequencies.get(corpus.charAt(i)) + 1;
    			frequencies.replace(corpus.charAt(i), count);
    		}
    	}
    	// Step 2: Make nodes then add to priority queue
    	for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
    		HuffNode node = new HuffNode(entry.getKey(), entry.getValue());
    		probabilities.add(node);
    	}
    	
    	// Step 3: Construct Huffman Trie
    	while (probabilities.size() > 1) {
    		// Remove two smallest
    		HuffNode child1 = probabilities.poll();
    		HuffNode child2 = probabilities.poll();
    		// Create new parent w/ summed probabilities of children --> Create new HuffNode w/ Null(?) char and summed prob
    		HuffNode newParent = new HuffNode('/', child1.count + child2.count);
    		// Enqueue new parent -- Add it to PriorityQueue(?)
    		probabilities.add(newParent);		
    	}
    	// Last node in PriorityQueue (probabilities) is root
    	trieRoot = probabilities.poll();
    	
    	// Step 4: Create Encoding Map --> DFS starting with trieRoot
    	createEncodingMap(trieRoot, "");
    	
    }
    
    
    // -----------------------------------------------
    // Compression
    // -----------------------------------------------
    
    /**
     * Compresses the given String message / text corpus into its Huffman coded
     * bitstring, as represented by an array of bytes. Uses the encodingMap
     * field generated during construction for this purpose.
     * @param message String representing the corpus to compress.
     * @return {@code byte[]} representing the compressed corpus with the
     *         Huffman coded bytecode. Formatted as 3 components: (1) the
     *         first byte contains the number of characters in the message,
     *         (2) the bitstring containing the message itself, (3) possible
     *         0-padding on the final byte.
     */
    public byte[] compress (String message) {
    	byte charCount = 0; // Equals amount of characters in encodingMap
    	
        throw new UnsupportedOperationException();
    }
    
    
    // -----------------------------------------------
    // Decompression
    // -----------------------------------------------
    
    /**
     * Decompresses the given compressed array of bytes into their original,
     * String representation. Uses the trieRoot field (the Huffman Trie) that
     * generated the compressed message during decoding.
     * @param compressedMsg {@code byte[]} representing the compressed corpus with the
     *        Huffman coded bytecode. Formatted as 3 components: (1) the
     *        first byte contains the number of characters in the message,
     *        (2) the bitstring containing the message itself, (3) possible
     *        0-padding on the final byte.
     * @return Decompressed String representation of the compressed bytecode message.
     */
    public String decompress (byte[] compressedMsg) {
        throw new UnsupportedOperationException();
    }
    
    
    // -----------------------------------------------
    // Huffman Trie
    // -----------------------------------------------
    

    public void createEncodingMap(HuffNode node, String encoding) {
    	if (node.isLeaf()) {
    		encodingMap.put(node.character, encoding);
    	}
    	if (node.left != null) {
    		createEncodingMap(node.left, encoding + "0");
    	}
    	if (node.right != null) {
    		createEncodingMap(node.right, encoding + "1");
    	}

    }
    
    /**
     * Huffman Trie Node class used in construction of the Huffman Trie.
     * Each node is a binary (having at most a left and right child), contains
     * a character field that it represents (in the case of a leaf, otherwise
     * the null character \0), and a count field that holds the number of times
     * the node's character (or those in its subtrees) appear in the corpus.
     */
    private static class HuffNode implements Comparable<HuffNode> {
        
        HuffNode left, right;
        char character;
        int count;
        
        HuffNode (char character, int count) {
            this.count = count;
            this.character = character;
        }
        
        public boolean isLeaf () {
            return left == null && right == null;
        }
        
        public int compareTo (HuffNode other) {
            return this.count - other.count;
        }
        
    }

}
