package io.konekto.backoffice.service.impl;

import io.konekto.backoffice.domain.BoData;
import io.konekto.backoffice.domain.dto.property.AppInfoDTO;
import io.konekto.backoffice.domain.dto.request.AppVersionRequestDTO;
import io.konekto.backoffice.domain.dto.AppVersionDto;
import io.konekto.backoffice.domain.enumration.DataKeyType;
import io.konekto.backoffice.domain.enumration.MobileType;
import io.konekto.backoffice.repository.BoDataRepository;
import io.konekto.backoffice.service.AppService;
import io.konekto.backoffice.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    BoDataRepository boDataRepository;

    @Override
    public AppVersionDto updateAppVersion(AppVersionRequestDTO requestDTO) {
        Map<String, Object> value = new HashMap<>();
        value.put("url", requestDTO.getUrl());
        value.put("version", requestDTO.getVersion());
        value.put("force_upgrade", requestDTO.isForceUpgrade());

        switch (requestDTO.getType()) {
            case IOS:
                BoData boData = new BoData();
                Optional<BoData> boDataOptional = boDataRepository.findByDataKey(DataKeyType.IOS_VERSION);
                if (boDataOptional.isPresent()) {
                    boData = boDataOptional.get();
                }

                boData.setDataKey(DataKeyType.IOS_VERSION);
                boData.setDataValue(JSONUtil.objToString(value));
                boDataRepository.save(boData);
                return getAppVersion(Collections.singletonList(MobileType.IOS));
            case ANDROID:
                BoData androidBoData = new BoData();
                Optional<BoData> androidBoDataOptional = boDataRepository.findByDataKey(DataKeyType.ANDROID_VERSION);
                if (androidBoDataOptional.isPresent()) {
                    androidBoData = androidBoDataOptional.get();
                }

                androidBoData.setDataKey(DataKeyType.ANDROID_VERSION);
                androidBoData.setDataValue(JSONUtil.objToString(value));
                boDataRepository.save(androidBoData);
                return getAppVersion(Collections.singletonList(MobileType.ANDROID));
        }

        return null;
    }

    @Override
    public AppVersionDto getAppVersion(List<MobileType> types) {
        List<DataKeyType> dataKeyTypes = new ArrayList<>();
        if (types.isEmpty() || types.contains(MobileType.IOS)) {
            dataKeyTypes.add(DataKeyType.IOS_VERSION);
        }
        if (types.isEmpty() || types.contains(MobileType.ANDROID)) {
            dataKeyTypes.add(DataKeyType.ANDROID_VERSION);
        }

        AppVersionDto responseDTO = new AppVersionDto();
        List<BoData> boDataList = boDataRepository.findByDataKeyIn(dataKeyTypes);
        for (BoData item : boDataList) {
            switch (item.getDataKey()) {
                case IOS_VERSION:
                    AppInfoDTO iosInfo = JSONUtil.stringToObj(item.getDataValue(), AppInfoDTO.class);
                    responseDTO.setIos(iosInfo);
                    break;
                case ANDROID_VERSION:
                    AppInfoDTO androidInfo = JSONUtil.stringToObj(item.getDataValue(), AppInfoDTO.class);
                    responseDTO.setAndroid(androidInfo);
                    break;
            }
        }

        return responseDTO;
    }
}
