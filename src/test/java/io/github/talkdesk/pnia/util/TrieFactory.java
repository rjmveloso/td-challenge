package io.github.talkdesk.pnia.util;

import java.io.FileInputStream;
import java.io.IOException;

import io.github.talkdesk.pnia.io.BufferedInputStreamReader;
import io.github.talkdesk.pnia.io.StreamReader;

public class TrieFactory {

	public static Trie createTrie() throws IOException {
		Trie trie = new ArrayTrie();
		try (StreamReader reader = new BufferedInputStreamReader(
				new FileInputStream("src/main/resources/prefixes.txt"))) {
			reader.stream().forEach(trie::add);
		}
		return trie;
	}

}
