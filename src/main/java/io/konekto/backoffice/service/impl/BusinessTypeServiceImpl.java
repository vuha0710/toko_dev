package io.konekto.backoffice.service.impl;

import io.konekto.backoffice.domain.dto.BusinessTypeDto;
import io.konekto.backoffice.service.BusinessTypeService;
import io.konekto.backoffice.service.client.PosClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessTypeServiceImpl implements BusinessTypeService {

    @Autowired
    PosClient posClient;

    @Override
    public List<BusinessTypeDto> findAll(String languageId) {
        return posClient.getBusinessTypes(languageId);
    }

}
