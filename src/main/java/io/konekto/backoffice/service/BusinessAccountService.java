package io.konekto.backoffice.service;

import io.konekto.backoffice.domain.dto.BusinessAccountDto;
import io.konekto.backoffice.domain.dto.request.BusinessAccountActionRequestDTO;
import io.konekto.backoffice.domain.dto.request.BusinessAccountRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BusinessAccountService {

    Page<BusinessAccountDto> getBusinessAccounts(BusinessAccountRequestDTO DTO, Pageable pageable);

    BusinessAccountDto getBusinessAccount(String id);

    void updateBusinessVerifyStatus(BusinessAccountActionRequestDTO requestDTO);
}
