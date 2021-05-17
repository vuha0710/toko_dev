package io.konekto.backoffice.service.impl;

import io.konekto.backoffice.domain.dto.LanguageDto;
import io.konekto.backoffice.service.LanguageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService {

    private static final String IN_LANGUAGE_CODE = "in";
    private static final String EN_LANGUAGE_CODE = "en";
    public static final String IN_LANGUAGE_NAME = "Bahasa Indonesia";
    public static final String EN_LANGUAGE_NAME = "English";

    @Override
    public List<LanguageDto> findAll() {
        LanguageDto en = new LanguageDto().setId(EN_LANGUAGE_CODE).setName(EN_LANGUAGE_NAME);
        LanguageDto id = new LanguageDto().setId(IN_LANGUAGE_CODE).setName(IN_LANGUAGE_NAME);
        return List.of(en, id);
    }
}
