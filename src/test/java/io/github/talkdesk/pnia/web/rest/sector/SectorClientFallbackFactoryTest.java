package io.github.talkdesk.pnia.web.rest.sector;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import feign.hystrix.FallbackFactory;
import io.github.talkdesk.pnia.web.rest.sector.SectorClientConfiguration.SectorClientFallbackFactory;

public class SectorClientFallbackFactoryTest {

	private FallbackFactory<FeignSectorClient> factory = new SectorClientFallbackFactory();
	
	@Test
	void testFallbackFactoryResult() {
		FeignSectorClient client = factory.create(new Exception("Bad Request"));
		assertSame(Optional.empty(), client.getSectorByNumber("+351910000000"));
	}

}
