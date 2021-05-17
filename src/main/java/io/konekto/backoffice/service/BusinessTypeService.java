package io.konekto.backoffice.service;

import io.konekto.backoffice.domain.dto.BusinessTypeDto;

import java.util.List;

public interface BusinessTypeService {

    List<BusinessTypeDto> findAll(String languageId);

}
