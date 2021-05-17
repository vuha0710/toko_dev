package io.konekto.backoffice.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@Accessors(chain = true)
public class BusinessTypeDto {

    private String id;
    private String name;

}
