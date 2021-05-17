package io.konekto.backoffice.util;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Setter
@Getter
@SuperBuilder
public class WebClientParam {

    private Map<String, String> header;
    private Map<String, Object> requestParam;
    private Object requestBody;
    private String url;

}
