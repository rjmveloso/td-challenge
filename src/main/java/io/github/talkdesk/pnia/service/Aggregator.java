package io.github.talkdesk.pnia.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import io.github.talkdesk.pnia.domain.Aggregation;

class Aggregator {

	final ConcurrentMap<String, InternalAggregation> data;
	//final ConcurrentNavigableMap<String, ? super Aggregation> data;

	Aggregator(int size) {
		data = new ConcurrentHashMap<>(size);
		//data = new ConcurrentSkipListMap<>();
	}

	class InternalAggregation implements Aggregation {
		private String prefix;

		private ConcurrentMap<String, Integer> sectors = new ConcurrentHashMap<>();

		InternalAggregation(String prefix) {
			this.prefix = prefix;
		}

		@Override
		public String getPrefix() {
			return prefix;
		}

		public Map<String, Integer> getSectors() {
			return sectors;
		}
	}

	public void aggregate(String prefix, String sector) {
		data.computeIfAbsent(prefix, key -> new InternalAggregation(prefix)).sectors.merge(sector, 1, Integer::sum);
	}

}
