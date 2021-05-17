package io.konekto.backoffice.util;

import io.konekto.backoffice.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RestTemplateUtil {

    public static <T> T post(String url, Object requestBody, String accessToken, Class<T> type) {
        return rest(HttpMethod.POST, url, requestBody, new HashMap<>(), accessToken, type);
    }

    public static <T> T get(String url, Map<String, Object> requestParam, String accessToken, Class<T> type) {
        return rest(HttpMethod.GET, url, null, requestParam, accessToken, type);
    }

    public static <T> T rest(HttpMethod httpMethod, String url, Object requestBody, Map<String, Object> requestParam, String accessToken, Class<T> type) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        RestTemplate restTemplate = new RestTemplate(factory);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        for(Map.Entry<String, Object> entry : requestParam.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            builder.queryParam(key, value);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        if (StringUtils.isNotEmpty(accessToken)) {
            headers.add("Authorization", accessToken);
        }
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(builder.build().toUriString(), httpMethod, entity, type);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            }
        } catch (Exception e) {
            log.error("RestTemplate Exception", e);
            throw new BadRequestException("Cannot connect to Server: " + builder.build().toUriString());
        }
        return null;
    }

}
