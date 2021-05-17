package io.konekto.backoffice.service.mapper;

import io.konekto.backoffice.domain.District;
import io.konekto.backoffice.domain.dto.GeolocationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DistrictMapper {

    public GeolocationDto toDTO(District district) {
        if (district == null) {
            return null;
        }
        GeolocationDto item = new GeolocationDto();
        item.setId(district.getId());
        item.setDistrict(district.getName());
        return item;
    }

    public List<GeolocationDto> toDTOList(List<District> list) {
        return list.stream()
            .filter(Objects::nonNull)
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}
