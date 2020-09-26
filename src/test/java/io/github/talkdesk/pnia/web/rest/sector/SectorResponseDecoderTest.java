package io.github.talkdesk.pnia.web.rest.sector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class SectorResponseDecoderTest {

	@Test
	void testValidPath() {
		final String content = "{\"number\": \"+351910000000\", \"sector\": \"Banking\"}";
		assertEquals("Banking", SectorResponseDecoder.decode(content, "sector"));
	}

	@Test
	void testInvalidPath() {
		final String content = "{\"number\": \"+351910000000\"}";
		assertEquals("", SectorResponseDecoder.decode(content, "sector"));
	}

	@Test
	void testInvalidJson() {
		final String content = "{\"number\":}";
		assertNull(SectorResponseDecoder.decode(content, "sector"));
	}

}
