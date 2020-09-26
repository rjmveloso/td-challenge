package io.github.talkdesk.pnia.web.rest.sector;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class RestSectorClient implements SectorApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestSectorClient.class);

	private String baseurl;
	private RestTemplate template;

	public RestSectorClient(String baseurl, RestTemplate template) {
		this.baseurl = baseurl;
		this.template = template;
	}

	@Override
	public Optional<String> getSectorByNumber(String number) {
		LOGGER.debug("Retrieving sector for number {}", number);

		try {
			ResponseEntity<String> response = template.getForEntity(baseurl + "/{number}", String.class, number);
			if (response.getStatusCode().is2xxSuccessful()) {
				String sector = SectorResponseDecoder.decode(response.getBody(), "sector");
				return Optional.ofNullable(sector);
			} else if (response.getStatusCodeValue() >= 400) {
				LOGGER.error("Error retrieving sector {}", response.getBody());
			}
		} catch (RestClientException e) {
			LOGGER.error("Error retrieving sector {}", e.getMessage());
		}

		return Optional.empty();
	}

}
