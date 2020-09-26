package io.github.talkdesk.pnia.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class BufferedInputStreamReaderTest {

	@Test
	void verifyCorrectStreamLine() throws IOException {
		final InputStream istream = new ByteArrayInputStream("test\n".getBytes());

		try (StreamReader reader = new BufferedInputStreamReader(istream)) {
			// prevent stream consumption
			Set<String> result = reader.stream().collect(Collectors.toSet());
			assertEquals(1, result.size());
			assertEquals("test", result.iterator().next());
		}
	}

}
