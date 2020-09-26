package io.github.talkdesk.pnia.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ArrayTrieTest {

	private Trie trie = new ArrayTrie();

	@Test
	public void veriyPrefixExtraction() {
		trie.add("+351");
		
		String prefix = trie.find("+351910000000");
		assertEquals("+351", prefix);
	}

}
