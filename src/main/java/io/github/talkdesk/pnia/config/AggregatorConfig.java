package io.github.talkdesk.pnia.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import io.github.talkdesk.pnia.io.BufferedInputStreamReader;
import io.github.talkdesk.pnia.io.StreamReader;
import io.github.talkdesk.pnia.repository.InMemoryPrefixRepository;
import io.github.talkdesk.pnia.repository.PrefixRepository;
import io.github.talkdesk.pnia.repository.RemoteSectorRepository;
import io.github.talkdesk.pnia.repository.SectorRepository;
import io.github.talkdesk.pnia.service.AggregatorService;
import io.github.talkdesk.pnia.service.DefaultAggregatorService;
import io.github.talkdesk.pnia.service.PhoneNumberSanitizer;
import io.github.talkdesk.pnia.service.PhoneNumberValidator;
import io.github.talkdesk.pnia.util.ArrayTrie;
import io.github.talkdesk.pnia.util.Trie;
import io.github.talkdesk.pnia.web.rest.sector.SectorApi;

@Configuration
public class AggregatorConfig {

	@Autowired
	private ResourceLoader resourceLoader;

	@Value("${pnia.prefix.source.file}")
	private String prefixSourceFile;

	@Bean
	public SectorRepository sectorRepository(SectorApi client) {
		return new RemoteSectorRepository(client);
	}

	@Bean
	public PrefixRepository prefixRepository() throws IOException {
		Resource resource = resourceLoader.getResource(prefixSourceFile);
		Trie trie = load(new ArrayTrie(), resource);
		return new InMemoryPrefixRepository(trie, new PhoneNumberSanitizer());
	}

	private Trie load(Trie trie, Resource resource) throws IOException {
		try (StreamReader reader = new BufferedInputStreamReader(resource.getInputStream())) {
			reader.stream().forEach(trie::add);
		}
		return trie;
	}

	@Bean
	public AggregatorService aggregatorService(PrefixRepository prefixRepository, SectorRepository sectorRepository) {
		return new DefaultAggregatorService(new PhoneNumberValidator(), prefixRepository, sectorRepository);
	}

}
