package io.github.talkdesk.pnia.web.rest.sector;

import java.util.Optional;

public interface SectorApi {

	Optional<String> getSectorByNumber(String number);

}
