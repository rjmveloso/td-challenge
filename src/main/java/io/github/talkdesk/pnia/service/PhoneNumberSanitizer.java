package io.github.talkdesk.pnia.service;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhoneNumberSanitizer implements NumberSanitizer {

	private static final Logger LOGGER = LoggerFactory.getLogger(PhoneNumberSanitizer.class);

	private static final Pattern PATTERN = Pattern.compile("[+|00]?(\\d|\\s)+");

	@Override
	public String sanitize(String value) {
		LOGGER.debug("Sanitizing number {}", value);

		if (value == null || !PATTERN.matcher(value.trim()).matches()) {
			LOGGER.debug("Invalid number prefix {}", value);
			return null;
		}

		if(value.startsWith("00")) {
			value = value.substring(2);
		}
		
		// remove '+' and all white spaces
		value = value.replaceAll("\\+|\\s+", "");
		LOGGER.debug("Sanitized number {}", value);

		return value;
	}

}
