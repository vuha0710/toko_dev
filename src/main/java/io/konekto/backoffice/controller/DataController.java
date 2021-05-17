package io.konekto.backoffice.controller;

import io.konekto.backoffice.domain.dto.*;
import io.konekto.backoffice.domain.enumration.GeolocationType;
import io.konekto.backoffice.service.BusinessTypeService;
import io.konekto.backoffice.service.GeolocationService;
import io.konekto.backoffice.service.LanguageService;
import io.konekto.backoffice.service.SubBusinessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DataController {

    @Autowired
    GeolocationService geolocationService;

    @Autowired
    BusinessTypeService businessTypeService;

    @Autowired
    SubBusinessTypeService subBusinessTypeService;

    @Autowired
    LanguageService languageService;

    @GetMapping("/data/geolocation/type/{type}")
    public ResponseEntity<List<GeolocationDto>> getGeolocationByType(@PathVariable("type") GeolocationType type,
                                                                     @RequestParam(required = false) String id) {
        List<GeolocationDto> list = geolocationService.getGeolocationByType(type, id);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/data/languages")
    public ResponseEntity<List<LanguageDto>> getLanguages() {
        List<LanguageDto> list = languageService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/data/business-types")
    public ResponseEntity<List<BusinessTypeDto>> getBusinessType(@RequestParam(defaultValue = "en") String languageCode) {
        List<BusinessTypeDto> list = businessTypeService.findAll(languageCode);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/data/sub-business-types")
    public ResponseEntity<List<BusinessTypeDto>> getSubBusinessType(@RequestParam String businessTypeId,
                                                                    @RequestParam String languageCode) {
        List<BusinessTypeDto> list = subBusinessTypeService.findAll(businessTypeId, languageCode);
        return ResponseEntity.ok(list);
    }

}
