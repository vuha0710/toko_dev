package io.konekto.backoffice.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class LanguageDto {

    private String id;
    private String name;
}
