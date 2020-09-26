package io.github.talkdesk.pnia.util;

public class ArrayTrie implements Trie {

	private class TrieNode {
		// '+' and numbers from 0-9, where '+' is indexed to 0
		private TrieNode[] children = new TrieNode[10];
	}

	private TrieNode root = new TrieNode();

	@Override
	public void add(String word) {
		TrieNode node = root;
		for (int i = 0; i < word.length(); i++) {
			int index = indexed(word.charAt(i));
			
			if (node.children[index] == null) {
				TrieNode child = new TrieNode();
				node.children[index] = child;
				node = child;
			} else {
				node = node.children[index];
			}
		}
	}
	
	@Override
	public String find(String content) {
		// 7: max length of a prefix
		StringBuilder prefix = new StringBuilder(7);
		
		TrieNode node = root;
		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			int index = indexed(c);
			if (node.children[index] == null) {
				break;
			} else {
				TrieNode child = node.children[index];
				prefix.append(c);
				node = child;
			}
		}

		return prefix.length() > 0 ? prefix.toString() : null;
	}
	
	private int indexed(char c) {
		return c == '+' ? 0 : c - '0';
	}

}
