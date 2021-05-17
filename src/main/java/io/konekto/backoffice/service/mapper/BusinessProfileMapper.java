package io.konekto.backoffice.service.mapper;

import io.konekto.backoffice.domain.BusinessProfile;
import io.konekto.backoffice.domain.dto.BusinessProfileDto;
import io.konekto.backoffice.domain.enumration.VerifyStatusEnum;
import io.konekto.backoffice.repository.*;
import io.konekto.backoffice.service.client.PosClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service
public class BusinessProfileMapper {

    @Autowired
    CountryRepository countryRepository;
    @Autowired
    ProvinceRepository provinceRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    VillageRepository villageRepository;
    @Autowired
    PosClient posClient;

    public BusinessProfileDto toDto(BusinessProfile entity) {
        if (entity == null) {
            return null;
        }
        BusinessProfileDto dto = new BusinessProfileDto();
        dto.setUserId(entity.getUserId());
        dto.setAvatar(entity.getAvatar());
        dto.setBusinessCardColor(entity.getBusinessCardColor());
        dto.setBusinessCardName(entity.getBusinessCardName());
        dto.setBusinessCardTemplate(entity.getBusinessCardTemplate());
        dto.setBusinessCardType(entity.getBusinessCardType());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setEmail(entity.getEmail());
        dto.setEntityType(entity.getEntityType());
        dto.setLanguage(entity.getLanguage());
        dto.setMobile(entity.getMobile());
        dto.setPhone(entity.getPhone());
        dto.setPostalCode(entity.getPostalCode());
        dto.setName(entity.getName());
        dto.setUpdatedAt(entity.getUpdatedAt());

        String verifyStatus;
        if (Arrays.asList(VerifyStatusEnum.ACTIVE.name(), VerifyStatusEnum.REJECTED.name())
                .contains(entity.getVerifyStatus())) {
            verifyStatus = entity.getVerifyStatus();
        } else {
            verifyStatus = VerifyStatusEnum.PENDING.name();
        }
        dto.setVerifyStatus(verifyStatus);

        dto.setWebsite(entity.getWebsite());
        dto.setBusinessId(entity.getBusinessId());

        if (StringUtils.isNotEmpty(entity.getType())) {
            Map<String, String> mapBusinessTypes = posClient.getMapBusinessTypes(PosClient.EN_LANGUAGE_ID);
            dto.setType(mapBusinessTypes.get(entity.getType()));
        }

        if (StringUtils.isNotEmpty(entity.getSubType())) {
            Map<String, String> mapSubBusinessTypes = posClient.getMapSubBusinessTypes(entity.getType(), PosClient.EN_LANGUAGE_ID);
            dto.setSubType(mapSubBusinessTypes.get(entity.getSubType()));
        }

        StringBuilder address = new StringBuilder();
        if (StringUtils.isNotEmpty(entity.getVillage())) {
            villageRepository.findById(entity.getVillage()).ifPresent(item -> {
                address.append(item.getName());
            });
        }
        if (StringUtils.isNotEmpty(entity.getDistrict())) {
            districtRepository.findById(entity.getDistrict()).ifPresent(item -> {
                address.append(", ").append(item.getName());
            });
        }
        if (StringUtils.isNotEmpty(entity.getCity())) {
            cityRepository.findById(entity.getCity()).ifPresent(item -> {
                address.append(", ").append(item.getName());
            });
        }
        if (StringUtils.isNotEmpty(entity.getProvince())) {
            provinceRepository.findById(entity.getProvince()).ifPresent(item -> {
                address.append(", ").append(item.getName());
            });
        }
        if (StringUtils.isNotEmpty(entity.getCountry())) {
            countryRepository.findById(entity.getCountry()).ifPresent(item -> {
                address.append(", ").append(item.getName());
            });
        }
        dto.setAddress(address.toString());

        return dto;
    }

}
