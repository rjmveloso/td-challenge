package io.github.talkdesk.pnia.io;

import java.io.Closeable;
import java.util.stream.Stream;

public interface StreamReader extends Closeable {

	Stream<String> stream();

}
