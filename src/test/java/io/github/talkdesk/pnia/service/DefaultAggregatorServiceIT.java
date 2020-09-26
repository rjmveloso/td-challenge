package io.github.talkdesk.pnia.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import io.github.talkdesk.pnia.domain.Aggregation;
import io.github.talkdesk.pnia.repository.InMemoryPrefixRepository;
import io.github.talkdesk.pnia.repository.PrefixRepository;
import io.github.talkdesk.pnia.repository.RemoteSectorRepository;
import io.github.talkdesk.pnia.repository.SectorRepository;
import io.github.talkdesk.pnia.util.Trie;
import io.github.talkdesk.pnia.util.TrieFactory;
import io.github.talkdesk.pnia.web.rest.sector.RestSectorClient;
import io.github.talkdesk.pnia.web.rest.sector.SectorApi;

@Tag("integration-test")
class DefaultAggregatorServiceIT {

	private static Trie trie;

	private SectorApi client = new RestSectorClient("https://challenge-business-sector-api.meza.talkdeskstg.com/sector",
			new RestTemplate());

	private NumberValidator validator = new PhoneNumberValidator();
	private PrefixRepository prefixRepository = new InMemoryPrefixRepository(trie, new PhoneNumberSanitizer());
	private SectorRepository sectorRepository = new RemoteSectorRepository(client);

	private AggregatorService service = new DefaultAggregatorService(validator, prefixRepository, sectorRepository);

	@BeforeAll
	static void initialize() throws IOException {
		trie = TrieFactory.createTrie();
	}

	@Test
	void verifyValidNumberAndSector() {
		Collection<Aggregation> result = service.aggregate(
				Arrays.asList("+1983236248", "+1 7490276403", "001382355A", "+351917382672", "+35191734022"));
		assertNotNull(result);
		assertNotEquals(0, result.size());
	}

}
