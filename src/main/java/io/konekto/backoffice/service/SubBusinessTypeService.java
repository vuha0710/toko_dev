package io.konekto.backoffice.service;

import io.konekto.backoffice.domain.dto.BusinessTypeDto;

import java.util.List;

public interface SubBusinessTypeService {

    List<BusinessTypeDto> findAll(String businessTypeId, String languageCode);

}
