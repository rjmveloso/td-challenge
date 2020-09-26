package io.github.talkdesk.pnia.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class BufferedInputStreamReader implements StreamReader, Closeable {

	private BufferedReader reader;

	public BufferedInputStreamReader(InputStream istream) throws IOException {
		reader = new BufferedReader(new InputStreamReader(istream));
	}

	@Override
	public Stream<String> stream() {
		return reader.lines();
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}

}
