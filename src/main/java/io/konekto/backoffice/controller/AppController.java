package io.konekto.backoffice.controller;

import io.konekto.backoffice.domain.dto.AppVersionDto;
import io.konekto.backoffice.domain.dto.request.AppVersionRequestDTO;
import io.konekto.backoffice.domain.enumration.MobileType;
import io.konekto.backoffice.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    AppService appService;

    @PutMapping("/app/versions")
    public ResponseEntity<AppVersionDto> updateAppVersion(@Valid @RequestBody AppVersionRequestDTO requestDTO) {
        AppVersionDto response = appService.updateAppVersion(requestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/public/app/versions")
    public ResponseEntity<AppVersionDto> GetAppVersion(@RequestParam(required = false) List<String> types) {
        List<MobileType> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(types)) {
            types.forEach(type -> {
                list.add(MobileType.fromValue(type));
            });
        }
        AppVersionDto response = appService.getAppVersion(list);
        return ResponseEntity.ok(response);
    }

}
