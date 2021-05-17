package io.konekto.backoffice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

/**
 * WebClient bean class
 */
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        ConnectionProvider provider = ConnectionProvider.builder("custom")
            .maxConnections(10)
            .maxIdleTime(Duration.ofSeconds(60))
            .build();

        HttpClient client = HttpClient.create(provider)
            .responseTimeout(Duration.ofSeconds(10));

        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(client))
            .build();
    }

}
