package io.github.talkdesk.pnia.domain;

import java.util.Map;

public interface Aggregation {

	String getPrefix();

	Map<String, Integer> getSectors();

}
