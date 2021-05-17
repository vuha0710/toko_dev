package io.konekto.backoffice.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusinessAccountDto {

    private String userId;
    private BusinessProfileDto business;
    private PersonalProfileDto personal;

}
