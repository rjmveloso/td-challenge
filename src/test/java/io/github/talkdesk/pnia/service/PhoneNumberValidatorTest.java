package io.github.talkdesk.pnia.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PhoneNumberValidatorTest {

	private NumberValidator validator = new PhoneNumberValidator();

	@Test
	void testNumberValidator() {
		assertTrue(validator.validate(" +198 "));
		assertTrue(validator.validate("1983236248"));
		assertTrue(validator.validate("+1 7490276403"));
		assertFalse(validator.validate("+351938"));
		assertFalse(validator.validate("001382355A"));
	}

}
