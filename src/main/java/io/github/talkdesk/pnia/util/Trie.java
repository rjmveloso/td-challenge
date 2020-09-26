package io.github.talkdesk.pnia.util;

public interface Trie {

	/**
	 * Add a word to be indexed in the Trie structure
	 * 
	 * @param word word to be indexed
	 */
	void add(String word);

	/**
	 * Find the longest word indexed
	 * 
	 * @param content content to search for a prefix
	 * @return the prefix found or null otherwise
	 */
	String find(String content);

}
