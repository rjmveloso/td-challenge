package io.github.talkdesk.pnia.web.rest.sector;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.hystrix.FallbackFactory;

public class SectorClientConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(FeignSectorClient.class);

	@Bean
	public Decoder decoder() {
		return new SectorDecoder();
	}

	@Bean
	public SectorClientFallbackFactory sectorClientFallbackFactory() {
		return new SectorClientFallbackFactory();
	}

	public static class SectorDecoder implements Decoder {
		@Override
		public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
			if (response.status() / 100 == 2) { // 2xx success series
				String sector = SectorResponseDecoder.decode(response.body().toString(), "sector");
				return Optional.ofNullable(sector);
			}

			LOGGER.error("Error retrieving sector {}", response.reason());
			return Optional.empty();
		}
	}

	/**
	 * Use a FallbackFactory to be able to log the exception message
	 */
	public static class SectorClientFallbackFactory implements FallbackFactory<FeignSectorClient> {
		@Override
		public FeignSectorClient create(Throwable cause) {
			LOGGER.error("Error retrieving sector {}", cause.getMessage());
			return new FeignSectorClient() {
				@Override
				public Optional<String> getSectorByNumber(String number) {
					return Optional.empty();
				}
			};
		}
	}

}
