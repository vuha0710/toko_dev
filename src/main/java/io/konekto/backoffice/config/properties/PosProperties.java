package io.konekto.backoffice.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "pos", ignoreUnknownFields = false)
public class PosProperties {

    private String baseUrl;
    private Long commonBuildNumber;
    private Uri uri;

    @Setter
    @Getter
    public static class Uri {
        private String getBusinessType;
        private String getSubBusinessType;
    }
}
