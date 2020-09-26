package io.github.talkdesk.pnia.repository;

import java.util.Optional;

import io.github.talkdesk.pnia.web.rest.sector.SectorApi;

public class RemoteSectorRepository implements SectorRepository {

	private SectorApi client;

	public RemoteSectorRepository(SectorApi client) {
		this.client = client;
	}

	@Override
	public Optional<String> collect(String number) {
		return client.getSectorByNumber(number);
	}

}
