package io.github.talkdesk.pnia.web.rest.sector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import feign.Request;
import feign.Request.Body;
import feign.Request.HttpMethod;
import feign.Response;
import feign.codec.Decoder;
import io.github.talkdesk.pnia.web.rest.sector.SectorClientConfiguration.SectorDecoder;

public class SectorDecoderTest {

	private final Decoder decoder = new SectorDecoder();

	@Test
	@SuppressWarnings("unchecked")
	void testValidResponse() {
		final String body = "{\"number\": \"+351910000000\", \"sector\": \"Banking\"}";
		Request request = Request.create(HttpMethod.GET, "", Collections.emptyMap(), Body.create(""), null);
		Response response = Response.builder().request(request).body(body, StandardCharsets.UTF_8).status(200).build();

		try {
			Object result = decoder.decode(response, null);
			assertEquals("Banking", ((Optional<String>) result).get());
		} catch (Exception e) {
			fail("Exception has been thrown: " + e.getMessage());
		}
	}

	@Test
	void testInvalidResponse() {
		Request request = Request.create(HttpMethod.GET, "", Collections.emptyMap(), Body.create(""), null);
		Response response = Response.builder().request(request).reason("Bad Request").status(404).build();

		try {
			Object result = decoder.decode(response, null);
			assertSame(Optional.empty(), result);
		} catch (Exception e) {
			fail("Exception has been thrown: " + e.getMessage());
		}
	}

}
