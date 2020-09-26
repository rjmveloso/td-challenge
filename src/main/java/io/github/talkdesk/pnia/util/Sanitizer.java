package io.github.talkdesk.pnia.util;

public interface Sanitizer<T> {

	T sanitize(T value);

}
