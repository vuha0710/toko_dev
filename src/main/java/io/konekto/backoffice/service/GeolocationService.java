package io.konekto.backoffice.service;

import io.konekto.backoffice.domain.dto.GeolocationDto;
import io.konekto.backoffice.domain.enumration.GeolocationType;

import java.util.List;

public interface GeolocationService {

    List<GeolocationDto> getGeolocationByType(GeolocationType type, String id);

}
