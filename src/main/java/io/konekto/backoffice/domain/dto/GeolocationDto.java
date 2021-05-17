package io.konekto.backoffice.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GeolocationDto {

    private String id;
    private String country;
    private String province;
    private String city;
    private String district;

}
