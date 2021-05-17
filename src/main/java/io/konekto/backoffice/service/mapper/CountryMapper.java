package io.konekto.backoffice.service.mapper;

import io.konekto.backoffice.domain.Country;
import io.konekto.backoffice.domain.dto.GeolocationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CountryMapper {

    public GeolocationDto toDTO(Country country) {
        if (country == null) {
            return null;
        }
        GeolocationDto item = new GeolocationDto();
        item.setId(country.getId());
        item.setCountry(country.getName());
        return item;
    }

    public List<GeolocationDto> toDTOList(List<Country> list) {
        return list.stream()
            .filter(Objects::nonNull)
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}
