package io.github.talkdesk.pnia.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PhoneNumberSanitizerTest {

	private NumberSanitizer sanitizer = new PhoneNumberSanitizer();

	@Test
	void testNumberSanitizer() {
		assertEquals("1983236248", sanitizer.sanitize("+1983236248"));
		assertEquals("17490276403", sanitizer.sanitize("+1 7490276403"));
		assertEquals("351917382672", sanitizer.sanitize("+351917382672"));
		assertEquals("351917382672", sanitizer.sanitize(" 351917382672"));
		assertNull(sanitizer.sanitize("001382355A"));
	}

}
