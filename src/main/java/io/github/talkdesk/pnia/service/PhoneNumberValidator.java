package io.github.talkdesk.pnia.service;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhoneNumberValidator implements NumberValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(PhoneNumberValidator.class);

	private static final Pattern PATTERN = Pattern.compile("(\\+|00)?((\\d\\s*){3})((\\d\\s*){4,9})?");

	@Override
	public boolean validate(String value) {
		LOGGER.debug("Validating number format {}", value);

		if (value == null || !PATTERN.matcher(value.trim()).matches()) {
			LOGGER.debug("Invalid number format {}", value);
			return false;
		}

		return true;
	}

}
