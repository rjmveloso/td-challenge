package io.github.talkdesk.pnia.web.rest.sector;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import feign.Contract;
import feign.Feign;
import io.github.talkdesk.pnia.web.rest.sector.SectorClientConfiguration.SectorDecoder;

@Tag("integration-test")
class FeignSectorClientIT {

	@Test
	void verifyValidNumberAndSector() {
		// Bind Spring MVC annotations to Feign
		final Contract contract = new SpringMvcContract();
		SectorApi client = Feign.builder().contract(contract).decoder(new SectorDecoder()).target(FeignSectorClient.class,
				"https://challenge-business-sector-api.meza.talkdeskstg.com/sector");

		Optional<String> sector = client.getSectorByNumber("+1983236248");
		assertEquals("Technology", sector.get());
	}

}
