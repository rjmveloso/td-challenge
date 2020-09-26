package io.github.talkdesk.pnia.service;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.talkdesk.pnia.domain.Aggregation;
import io.github.talkdesk.pnia.repository.PrefixRepository;
import io.github.talkdesk.pnia.repository.SectorRepository;

public class DefaultAggregatorService implements AggregatorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAggregatorService.class);

	private NumberValidator validator;
	private PrefixRepository prefixRepository;
	private SectorRepository sectorRepository;

	public DefaultAggregatorService(NumberValidator validator, PrefixRepository prefixRepository,
			SectorRepository sectorRepository) {
		this.validator = validator;
		this.prefixRepository = prefixRepository;
		this.sectorRepository = sectorRepository;
	}

	private Optional<String> findPrefix(String number) {
		Optional<String> prefix = prefixRepository.find(number);
		LOGGER.debug("Prefix for number {}: {}", number, prefix.orElse(EMPTY));
		return prefix;
	}

	@Override
	public Collection<Aggregation> aggregate(Collection<String> values) {
		if (values == null) {
			return Collections.emptySet();
		}

		// Assume at most a capacity of values.size();
		Aggregator aggregator = new Aggregator(values.size());

		LOGGER.debug("Aggregating values will start");

		values.stream().filter(validator::validate).forEach(number -> {
			findPrefix(number).ifPresent(prefix -> {
				collect(number, (sector) -> aggregator.aggregate(prefix, sector));
			});
		});

		final SortedSet<Aggregation> result = new TreeSet<>((s1, s2) -> {
			return s1.getPrefix().compareTo(s2.getPrefix());
		});
		result.addAll(aggregator.data.values());

		if (LOGGER.isDebugEnabled()) {
			result.forEach(entry -> {
				entry.getSectors().entrySet().forEach(entry2 -> {
					LOGGER.debug("Prefix: {} Sector: {} Count: {}", entry.getPrefix(), entry2.getKey(),
							entry2.getValue());
				});
			});
		}

		return result;
	}

	private void collect(String number, Consumer<String> callback) {
		LOGGER.debug("Calling sector repository for {}", number);
		sectorRepository.collect(number).ifPresent(sector -> callback.accept(sector));
	}

}
