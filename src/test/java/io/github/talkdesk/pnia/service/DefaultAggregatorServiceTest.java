package io.github.talkdesk.pnia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.talkdesk.pnia.domain.Aggregation;
import io.github.talkdesk.pnia.repository.PrefixRepository;
import io.github.talkdesk.pnia.repository.SectorRepository;

@ExtendWith(MockitoExtension.class)
class DefaultAggregatorServiceTest {

	@Mock
	private PrefixRepository prefixRepository;

	@Mock
	private SectorRepository sectorRepository;

	private NumberValidator validator = new PhoneNumberValidator();

	@InjectMocks
	private AggregatorService service = new DefaultAggregatorService(validator, prefixRepository, sectorRepository);

	private static final Map<String, String> DATA_TEST = new HashMap<>();

	static {
		DATA_TEST.put("+1", "Technology");
		DATA_TEST.put("+35191734", "Banking");
		DATA_TEST.put("+35191738", "Clothing");
	}

	@Test
	void verifyValidNumberAndSector() {
		when(prefixRepository.find(any())).thenAnswer((invocation) -> simulatePrefix(invocation.getArgument(0)));
		when(sectorRepository.collect(any())).thenAnswer((invocation) -> simulateSector(invocation.getArgument(0)));

		Collection<Aggregation> result = service.aggregate(
				Arrays.asList("+1983236248", "+1 7490276403", "001382355A", "+351917382672", "+35191734022"));

		assertNotNull(result);
		assertEquals(3, result.size());
	}

	private Optional<String> simulatePrefix(String number) {
		return DATA_TEST.keySet().stream().filter(number::startsWith).findFirst();
	}

	private Optional<String> simulateSector(String number) {
		return DATA_TEST.entrySet().stream().filter(e -> number.startsWith(e.getKey())).map(Entry::getValue)
				.findFirst();
	}
}
