package io.github.talkdesk.pnia.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import io.github.talkdesk.pnia.web.rest.sector.RestSectorClient;
import io.github.talkdesk.pnia.web.rest.sector.SectorApi;

public class RemoteSectorRepositoryTest {

	private SectorApi client = new RestSectorClient("https://challenge-business-sector-api.meza.talkdeskstg.com/sector", new RestTemplate());

	private SectorRepository repository = new RemoteSectorRepository(client);

	@Test
	public void testValidSector() {
		Optional<String> sector = repository.collect("+351917382672");
		assertTrue(sector.isPresent());
		assertEquals("Clothing", sector.get());
	}

}
