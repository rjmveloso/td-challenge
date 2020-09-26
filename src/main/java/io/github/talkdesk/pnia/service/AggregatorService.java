package io.github.talkdesk.pnia.service;

import java.util.Collection;

import io.github.talkdesk.pnia.domain.Aggregation;

public interface AggregatorService {

	Collection<Aggregation> aggregate(Collection<String> values);

}
