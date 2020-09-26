package io.github.talkdesk.pnia.repository;

import java.util.Optional;

public interface SectorRepository {

	Optional<String> collect(String number);

}
