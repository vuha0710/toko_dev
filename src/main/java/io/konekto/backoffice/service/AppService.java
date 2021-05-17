package io.konekto.backoffice.service;

import io.konekto.backoffice.domain.dto.request.AppVersionRequestDTO;
import io.konekto.backoffice.domain.dto.AppVersionDto;
import io.konekto.backoffice.domain.enumration.MobileType;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AppService {

    AppVersionDto updateAppVersion(@RequestBody AppVersionRequestDTO requestDTO);

    AppVersionDto getAppVersion(List<MobileType> type);

}
