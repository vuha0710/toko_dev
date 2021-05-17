package io.konekto.backoffice.service.mapper;

import io.konekto.backoffice.domain.City;
import io.konekto.backoffice.domain.dto.GeolocationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CityMapper {

    public GeolocationDto toDTO(City city) {
        if (city == null) {
            return null;
        }
        GeolocationDto item = new GeolocationDto();
        item.setId(city.getId());
        item.setCity(city.getName());
        return item;
    }

    public List<GeolocationDto> toDTOList(List<City> list) {
        return list.stream()
            .filter(Objects::nonNull)
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}
