package io.konekto.backoffice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PosBusinessTypeDto {

    private String id;

    private String name;

    @JsonProperty("language_id")
    private String languageId;

}
