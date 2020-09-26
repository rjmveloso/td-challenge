package io.github.talkdesk.pnia.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.talkdesk.pnia.service.PhoneNumberSanitizer;
import io.github.talkdesk.pnia.util.Trie;
import io.github.talkdesk.pnia.util.TrieFactory;

public class InMemoryPrefixRepositoryTest {

	private static Trie trie;

	private PrefixRepository repository = new InMemoryPrefixRepository(trie, new PhoneNumberSanitizer());

	@BeforeAll
	public static void initialize() throws IOException {
		trie = TrieFactory.createTrie();
	}

	@Test
	public void testValidPrefixMatcher() {
		Optional<String> prefix = repository.find("+3000450999999");
		assertTrue(prefix.isPresent());
		assertEquals("3000450", prefix.get());
	}

}
