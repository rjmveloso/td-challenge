package io.github.talkdesk.pnia.repository;

import java.util.Optional;

public interface PrefixRepository {

	Optional<String> find(String number);

}
