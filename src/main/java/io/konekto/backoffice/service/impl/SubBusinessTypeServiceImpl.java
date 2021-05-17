package io.konekto.backoffice.service.impl;

import io.konekto.backoffice.domain.dto.BusinessTypeDto;
import io.konekto.backoffice.service.SubBusinessTypeService;
import io.konekto.backoffice.service.client.PosClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubBusinessTypeServiceImpl implements SubBusinessTypeService {

    @Autowired
    PosClient posClient;

    @Override
    public List<BusinessTypeDto> findAll(String businessTypeId, String languageCode) {
        return posClient.getSubBusinessTypes(businessTypeId, languageCode);
    }

}
