package io.konekto.backoffice.service.client;

import io.konekto.backoffice.config.properties.PosProperties;
import io.konekto.backoffice.domain.dto.BusinessTypeDto;
import io.konekto.backoffice.domain.dto.PosBusinessTypeDto;
import io.konekto.backoffice.domain.dto.PosBusinessTypeResponse;
import io.konekto.backoffice.util.WebClientParam;
import io.konekto.backoffice.util.WebClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PosClient {

    private static final String IN_LANGUAGE_CODE = "in";
    private static final String EN_LANGUAGE_CODE = "en";
    public static final String IN_LANGUAGE_ID = "7102c45a-3340-45c9-8ef9-b661ac837620";
    public static final String EN_LANGUAGE_ID = "9f9a3305-f013-425d-8478-2f5334e02188";

    private static final List<String> otherBusinessTypes = List.of("OTHERS", "LAINNYA");

    private final PosProperties posProperties;
    private final WebClient webClient;

    @Autowired
    public PosClient(PosProperties posProperties, WebClient webClient) {
        this.posProperties = posProperties;
        this.webClient = webClient;
    }

    public Map<String, String> getMapBusinessTypes(String languageId) {
        String languageCode = IN_LANGUAGE_ID.equals(languageId) ? IN_LANGUAGE_CODE : EN_LANGUAGE_CODE;
        return getBusinessTypes(languageCode)
            .stream()
            .collect(Collectors.toMap(BusinessTypeDto::getId, BusinessTypeDto::getName));
    }

    public Map<String, String> getMapSubBusinessTypes(String businessTypeId, String languageId) {
        String languageCode = IN_LANGUAGE_ID.equals(languageId) ? IN_LANGUAGE_CODE : EN_LANGUAGE_CODE;
        return getSubBusinessTypes(businessTypeId, languageCode)
            .stream()
            .collect(Collectors.toMap(BusinessTypeDto::getId, BusinessTypeDto::getName));
    }

    @Cacheable("businessTypes")
    public List<BusinessTypeDto> getBusinessTypes(String languageCode) {
        String languageId = IN_LANGUAGE_CODE.equals(languageCode) ? IN_LANGUAGE_ID : EN_LANGUAGE_ID;

        StringBuilder url = new StringBuilder();
        url.append(posProperties.getBaseUrl()).append(posProperties.getUri().getGetBusinessType());
        url.append("/").append(languageId);

        log.info("Rest call POS with url: {}", url);
        return getBusinessTypesFunction(url, languageCode);
    }

    @Cacheable("subBusinessTypes")
    public List<BusinessTypeDto> getSubBusinessTypes(String businessTypeId, String languageCode) {
        String languageId = IN_LANGUAGE_CODE.equals(languageCode) ? IN_LANGUAGE_ID : EN_LANGUAGE_ID;

        StringBuilder url = new StringBuilder();
        url.append(posProperties.getBaseUrl()).append(posProperties.getUri().getGetSubBusinessType());

        if (StringUtils.hasText(businessTypeId)) {
            url.append("/").append(businessTypeId);
            url.append("/").append(languageId);
        } else {
            url.append("?language_id=").append(languageId);
        }

        log.info("Rest call POS with url: {}", url);
        return getBusinessTypesFunction(url, languageCode);
    }

    private List<BusinessTypeDto> getBusinessTypesFunction(StringBuilder url, String languageCode) {
        Map<String, String> header = new HashMap<>();
        header.put("common-build-number", posProperties.getCommonBuildNumber().toString());
        header.put("accept-language", languageCode);

        WebClientParam webClientParam = WebClientParam.builder()
            .header(header)
            .url(url.toString())
            .build();

        PosBusinessTypeResponse posResponse = WebClientUtil.call(
            webClient,
            webClientParam,
            HttpMethod.GET,
                PosBusinessTypeResponse.class
        );

        return getBusinessTypeInfoResponses(posResponse);
    }

    private List<BusinessTypeDto> getBusinessTypeInfoResponses(PosBusinessTypeResponse posResponse) {
        if (posResponse == null || posResponse.getList() == null)
            return List.of();

        List<BusinessTypeDto> responses = posResponse.getList()
            .stream()
            .filter(this::isNotOtherBusinessType)
            .map(this::getBusinessTypeDTO)
            .collect(Collectors.toList());

        List<BusinessTypeDto> others = posResponse.getList()
            .stream()
            .filter(this::isOtherBusinessType)
            .map(this::getBusinessTypeDTO)
            .collect(Collectors.toList());

        responses.addAll(others);
        return responses;
    }

    private boolean isOtherBusinessType(PosBusinessTypeDto posBusinessTypeDto) {
        return otherBusinessTypes.contains(posBusinessTypeDto.getName().toUpperCase());
    }

    private boolean isNotOtherBusinessType(PosBusinessTypeDto posBusinessTypeDto) {
        return !otherBusinessTypes.contains(posBusinessTypeDto.getName().toUpperCase());
    }

    private BusinessTypeDto getBusinessTypeDTO(PosBusinessTypeDto posBusinessTypeDto) {
        return BusinessTypeDto
            .builder()
            .id(posBusinessTypeDto.getId())
            .name(posBusinessTypeDto.getName())
            .build();
    }
}
