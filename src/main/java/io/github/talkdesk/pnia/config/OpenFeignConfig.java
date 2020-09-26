package io.github.talkdesk.pnia.config;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import io.github.talkdesk.pnia.web.rest.sector.FeignSectorClient;

@Configuration
@EnableHystrix
@EnableFeignClients(clients = FeignSectorClient.class)
public class OpenFeignConfig {

}
