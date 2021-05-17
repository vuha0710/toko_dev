package io.konekto.backoffice.service.mapper;

import io.konekto.backoffice.domain.PersonalProfile;
import io.konekto.backoffice.domain.dto.PersonalProfileDto;
import io.konekto.backoffice.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalProfileMapper {

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

    public PersonalProfileDto toDto(PersonalProfile entity) {
        if (entity == null) {
            return null;
        }
        PersonalProfileDto DTO = new PersonalProfileDto();
        DTO.setUserId(entity.getUserId());
        DTO.setUserMobile(entity.getUserMobile());
        DTO.setReferralCode(entity.getReferralCode());
        DTO.setReferralLink(entity.getReferralLink());
        DTO.setName(entity.getName());
        DTO.setAvatar(entity.getAvatar());
        DTO.setEmail(entity.getEmail());
        DTO.setMobile(entity.getMobile());
        DTO.setDob(entity.getDob());
        DTO.setPostalCode(entity.getPostalCode());
        DTO.setRt(entity.getRt());
        DTO.setRw(entity.getRw());
        DTO.setIdCardNumber(entity.getIdCardNumber());
        DTO.setIdCardName(entity.getIdCardName());
        DTO.setIdCardPhoto(entity.getIdCardPhoto());
        DTO.setIdCardSelfie(entity.getIdCardSelfie());
        DTO.setReferrerId(entity.getReferrerId());
        DTO.setCreatedAt(entity.getCreatedAt());
        DTO.setUpdatedAt(entity.getUpdatedAt());
        DTO.setGender(entity.getGender());

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
        DTO.setAddress(address.toString());
        return DTO;
    }
}
