package io.konekto.backoffice.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.konekto.backoffice.domain.enumration.MobileType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class AppVersionRequestDTO {

    @NotEmpty
    private String type;

    @NotEmpty
    private String version;

    @NotEmpty
    private String url;

    @JsonProperty("force_upgrade")
    private boolean forceUpgrade;

    public MobileType getType() {
        return MobileType.fromValue(type);
    }
}
