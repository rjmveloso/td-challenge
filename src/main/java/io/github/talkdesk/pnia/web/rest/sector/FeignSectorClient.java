package io.github.talkdesk.pnia.web.rest.sector;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "sector", url = "${pnia.sector.base.url}", configuration = SectorClientConfiguration.class, fallbackFactory = SectorClientConfiguration.SectorClientFallbackFactory.class)
public interface FeignSectorClient extends SectorApi {

	@GetMapping("{number}")
	Optional<String> getSectorByNumber(@PathVariable("number") String number);

}
