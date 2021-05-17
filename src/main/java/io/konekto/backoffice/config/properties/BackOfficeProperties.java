package io.konekto.backoffice.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "backoffice", ignoreUnknownFields = false)
public class BackOfficeProperties {

    private final Security security = new Security();
    private final CorsConfiguration cors = new CorsConfiguration();
    private RabbitProperties rabbitmq = new RabbitProperties();

    @Setter
    @Getter
    public static class RabbitProperties {
        private String exchange;
        private String businessQueue;
        private String businessRoutingKey;
    }

    @Setter
    @Getter
    public static class Security {
        private final Authentication authentication = new Authentication();

        @Setter
        @Getter
        public static class Authentication {
            private String secret;
            private String base64Secret;
            private long tokenValidityInSeconds;
            private long tokenValidityInSecondsForRememberMe;
        }
    }
}
