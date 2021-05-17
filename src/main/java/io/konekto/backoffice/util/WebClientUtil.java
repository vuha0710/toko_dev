package io.konekto.backoffice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class WebClientUtil {

    public static <T> T call(WebClient webClient, WebClientParam webClientParam, HttpMethod httpMethod, Class<T> type) {

        Function<UriBuilder, URI> uriFunction = null;
        if (webClientParam.getRequestParam() != null)
            for (Map.Entry<String, Object> entry : webClientParam.getRequestParam().entrySet())
                uriFunction = uriBuilder -> uriBuilder.queryParam(entry.getKey(), entry.getValue()).build();

        switch (httpMethod) {
            case GET:
                WebClient.RequestHeadersUriSpec requestHeadersUriSpec = webClient.get();
                WebClient.RequestHeadersSpec requestHeadersSpec = uriFunction != null
                    ? requestHeadersUriSpec.uri(webClientParam.getUrl(), uriFunction)
                    : requestHeadersUriSpec.uri(webClientParam.getUrl());

                requestHeadersSpec
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                if (webClientParam.getHeader() != null) {
                    for (Map.Entry<String, String> entry : webClientParam.getHeader().entrySet()) {
                        requestHeadersSpec.header(entry.getKey(), entry.getValue());
                    }
                }

                return requestHeadersSpec
                    .retrieve()
                    .bodyToMono(type)
                    .block();
            case POST:
                WebClient.RequestBodyUriSpec requestBodyUriSpec = webClient.post();
                WebClient.RequestBodySpec requestBodySpec = uriFunction != null
                    ? requestBodyUriSpec.uri(webClientParam.getUrl(), uriFunction)
                    : requestBodyUriSpec.uri(webClientParam.getUrl());

                requestBodySpec
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

                if (webClientParam.getHeader() != null) {
                    for (Map.Entry<String, String> entry : webClientParam.getHeader().entrySet()) {
                        requestBodySpec.header(entry.getKey(), entry.getValue());
                    }
                }

                return requestBodySpec
                    .bodyValue(webClientParam.getRequestBody())
                    .retrieve()
                    .bodyToMono(type)
                    .block();
            case PUT:
                WebClient.RequestBodyUriSpec requestBodyPutUriSpec = webClient.put();
                WebClient.RequestBodySpec requestBodyPutSpec = uriFunction != null
                    ? requestBodyPutUriSpec.uri(webClientParam.getUrl(), uriFunction)
                    : requestBodyPutUriSpec.uri(webClientParam.getUrl());

                requestBodyPutSpec
                    .accept(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

                if (webClientParam.getHeader() != null) {
                    for (Map.Entry<String, String> entry : webClientParam.getHeader().entrySet()) {
                        requestBodyPutSpec.header(entry.getKey(), entry.getValue());
                    }
                }

                return requestBodyPutSpec
                    .bodyValue(webClientParam.getRequestBody())
                    .retrieve()
                    .bodyToMono(type)
                    .block();
            default:
                log.error("Not support HTTP Method Konekto");
        }

        return null;
    }


}
