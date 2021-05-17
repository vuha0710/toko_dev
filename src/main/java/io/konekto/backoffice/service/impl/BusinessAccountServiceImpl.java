package io.konekto.backoffice.service.impl;

import io.konekto.backoffice.domain.BusinessProfile;
import io.konekto.backoffice.domain.PersonalProfile;
import io.konekto.backoffice.domain.dto.BusinessAccountDto;
import io.konekto.backoffice.domain.dto.request.BusinessAccountActionRequestDTO;
import io.konekto.backoffice.domain.dto.request.BusinessAccountRequestDTO;
import io.konekto.backoffice.exception.BadRequestException;
import io.konekto.backoffice.repository.BusinessAccountRepository;
import io.konekto.backoffice.repository.BusinessProfileRepository;
import io.konekto.backoffice.repository.PersonalProfileRepository;
import io.konekto.backoffice.service.BusinessAccountService;
import io.konekto.backoffice.service.mapper.BusinessProfileMapper;
import io.konekto.backoffice.service.mapper.PersonalProfileMapper;
import io.konekto.backoffice.service.message.RabbitBusinessProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BusinessAccountServiceImpl implements BusinessAccountService {

    @Autowired
    BusinessAccountRepository businessAccountRepository;

    @Autowired
    BusinessProfileRepository businessProfileRepository;

    @Autowired
    PersonalProfileRepository personalProfileRepository;

    @Autowired
    BusinessProfileMapper businessProfileMapper;

    @Autowired
    PersonalProfileMapper personalProfileMapper;

    @Autowired
    RabbitBusinessProducer businessProducer;

    @Override
    public Page<BusinessAccountDto> getBusinessAccounts(BusinessAccountRequestDTO Dto, Pageable pageable) {
        Page<Object[]> accountProfiles = businessAccountRepository.getAccountProfile(
            Dto.getQ(),
            Dto.getProvince(),
            Dto.getCity(),
            Dto.getDistrict(),
            Dto.getBusinessType(),
            Dto.getSubBusinessType(),
            Dto.getBusinessEntity(),
            Dto.getVerifyStatus() != null ? Dto.getVerifyStatus().name() : null,
            pageable
        );

        List<BusinessAccountDto> dataList = new ArrayList<>();
        for (Object[] row : accountProfiles) {
            BusinessProfile businessProfile = (BusinessProfile) row[0];
            PersonalProfile personalProfile = (PersonalProfile) row[1];

            BusinessAccountDto businessAccountDTO = new BusinessAccountDto();
            businessAccountDTO.setBusiness(businessProfileMapper.toDto(businessProfile));
            businessAccountDTO.setPersonal(personalProfileMapper.toDto(personalProfile));
            businessAccountDTO.setUserId(personalProfile.getUserId());
            dataList.add(businessAccountDTO);
        }

        return new PageImpl<>(dataList, pageable, accountProfiles.getTotalElements());
    }

    @Override
    public BusinessAccountDto getBusinessAccount(String id) {
        Optional<BusinessProfile> businessProfile = businessProfileRepository.findById(id);
        Optional<PersonalProfile> personalProfile = personalProfileRepository.findById(id);
        if (businessProfile.isPresent() && personalProfile.isPresent()) {
            BusinessAccountDto businessAccountDTO = new BusinessAccountDto();
            businessAccountDTO.setBusiness(businessProfileMapper.toDto(businessProfile.get()));
            businessAccountDTO.setPersonal(personalProfileMapper.toDto(personalProfile.get()));
            businessAccountDTO.setUserId(id);
            return businessAccountDTO;
        }

        return null;
    }

    @Override
    @Async
    public void updateBusinessVerifyStatus(BusinessAccountActionRequestDTO requestDTO) {
        if (requestDTO.getUserId() == null || requestDTO.getVerifyStatus() == null) {
            log.error("UserId or VerifyStatus should be not empty");
            return;
        }
        businessProfileRepository.findById(requestDTO.getUserId())
            .ifPresent(businessProfile -> {
                if (StringUtils.isEmpty(businessProfile.getBusinessId())) {
                    throw new BadRequestException("BusinessId is empty. Please check system data.");
                }
                businessProducer.send(businessProfile.getBusinessId(), requestDTO.getVerifyStatus());
            });
    }

}
