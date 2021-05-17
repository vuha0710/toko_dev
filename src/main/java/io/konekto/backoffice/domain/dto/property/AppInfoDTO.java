package io.konekto.backoffice.domain.dto.property;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppInfoDTO {

    private String version;
    private String url;

    @JsonProperty("force_upgrade")
    private boolean forceUpgrade;
}
