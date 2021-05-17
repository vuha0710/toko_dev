package io.konekto.backoffice.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.konekto.backoffice.domain.dto.property.AppInfoDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppVersionDto {

    private AppInfoDTO ios;
    private AppInfoDTO android;

}
