package io.github.talkdesk.pnia.web.rest.sector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class RestSectorClientTest {

	@Mock
	private RestTemplate template;

	@InjectMocks
	private SectorApi client = new RestSectorClient("http://localhost", template);

	@Test
	void verifySuccessResponse() {
		final String number = "+351910000000";

		final String response = "{\"number\": \"+351910000000\", \"sector\": \"Banking\"}";
		when(template.getForEntity(anyString(), eq(String.class), anyString())).thenReturn(ResponseEntity.ok(response));

		Optional<String> result = client.getSectorByNumber(number);
		assertTrue(result.isPresent());
		assertEquals("Banking", result.get());
	}

	@Test
	void testUnsuccessResponse() {
		when(template.getForEntity(anyString(), eq(String.class), anyString())).thenReturn(ResponseEntity.badRequest().build());
		
		Optional<String> result = client.getSectorByNumber("+351910000000");
		assertFalse(result.isPresent());
	}

}
