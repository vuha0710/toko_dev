package io.konekto.backoffice.service;

import io.konekto.backoffice.domain.dto.LanguageDto;

import java.util.List;

public interface LanguageService {

    List<LanguageDto> findAll();

}
