package io.konekto.backoffice.service.mapper;

import io.konekto.backoffice.domain.Province;
import io.konekto.backoffice.domain.dto.GeolocationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProvinceMapper {

    public GeolocationDto toDTO(Province province) {
        if (province == null) {
            return null;
        }
        GeolocationDto item = new GeolocationDto();
        item.setId(province.getId());
        item.setProvince(province.getName());
        return item;
    }

    public List<GeolocationDto> toDTOList(List<Province> list) {
        return list.stream()
            .filter(Objects::nonNull)
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}
