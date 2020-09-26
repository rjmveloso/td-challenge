package io.github.talkdesk.pnia.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.github.talkdesk.pnia.domain.Aggregation;
import io.github.talkdesk.pnia.service.AggregatorService;

@RestController
@RequestMapping("/aggregate")
public class AggregateController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AggregateController.class);

	@Autowired
	private AggregatorService service;

	private final ObjectMapper mapper = new ObjectMapper();

	@ExceptionHandler(JsonProcessingException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public void handleJsonException(JsonProcessingException e) {
		
	}
	
	@ResponseBody
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public String aggregate(@RequestBody List<String> numbers) throws JsonProcessingException {
		LOGGER.info("[JSON] Aggregating sectors for {}", numbers);

		if (CollectionUtils.isEmpty(numbers)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message not found");
		}

		return process(numbers);
	}

	@ResponseBody
	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String aggregate(@RequestParam MultiValueMap<String, String> request) throws JsonProcessingException {
		LOGGER.info("[FORM] Aggregating sectors for {}", request.keySet());

		if (CollectionUtils.isEmpty(request.keySet())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message not found");
		}

		// Trick to parse submitted value as "application/x-www-form-urlencoded"
		String entry = request.keySet().iterator().next();
		String[] numbers = mapper.readValue(entry, String[].class);
		return process(Arrays.asList(numbers));
	}

	private String process(List<String> numbers) throws JsonProcessingException {
		Collection<Aggregation> result = service.aggregate(numbers);

		ObjectNode root = mapper.createObjectNode();

		for (Aggregation aggregation : result) {
			ObjectNode children = mapper.createObjectNode();

			for (Map.Entry<String, Integer> entry : aggregation.getSectors().entrySet()) {
				children.put(entry.getKey(), entry.getValue());
			}
			root.set(aggregation.getPrefix(), children);
		}

		return mapper.writeValueAsString(root);
	}
}
