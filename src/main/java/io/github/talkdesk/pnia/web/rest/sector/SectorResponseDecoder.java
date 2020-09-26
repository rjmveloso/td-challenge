package io.github.talkdesk.pnia.web.rest.sector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SectorResponseDecoder {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	public static String decode(String body, String path) {
		try {
			return MAPPER.readTree(body).path(path).asText();
		} catch (JsonProcessingException e) {
			return null;
		}
	}

}
