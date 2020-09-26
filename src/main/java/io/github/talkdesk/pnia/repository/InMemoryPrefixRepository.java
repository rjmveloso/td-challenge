package io.github.talkdesk.pnia.repository;

import java.util.Optional;

import io.github.talkdesk.pnia.service.NumberSanitizer;
import io.github.talkdesk.pnia.util.Trie;

public class InMemoryPrefixRepository implements PrefixRepository {

	private Trie trie;
	private NumberSanitizer sanitizer;

	public InMemoryPrefixRepository(Trie trie, NumberSanitizer sanitizer) {
		this.trie = trie;
		this.sanitizer = sanitizer;
	}

	@Override
	public Optional<String> find(String number) {
		return Optional.ofNullable(sanitizer.sanitize(number)).map(trie::find);
	}

}
