package io.github.talkdesk.pnia.util;

public interface Validator<T> {

	boolean validate(T value);

}
