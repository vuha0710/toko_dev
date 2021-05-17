package io.konekto.backoffice.service.impl;

import io.konekto.backoffice.domain.City;
import io.konekto.backoffice.domain.Country;
import io.konekto.backoffice.domain.District;
import io.konekto.backoffice.domain.Province;
import io.konekto.backoffice.domain.dto.GeolocationDto;
import io.konekto.backoffice.domain.enumration.GeolocationType;
import io.konekto.backoffice.repository.CityRepository;
import io.konekto.backoffice.repository.CountryRepository;
import io.konekto.backoffice.repository.DistrictRepository;
import io.konekto.backoffice.repository.ProvinceRepository;
import io.konekto.backoffice.service.GeolocationService;
import io.konekto.backoffice.service.mapper.CityMapper;
import io.konekto.backoffice.service.mapper.CountryMapper;
import io.konekto.backoffice.service.mapper.DistrictMapper;
import io.konekto.backoffice.service.mapper.ProvinceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeolocationServiceImpl implements GeolocationService {

    @Autowired
    ProvinceMapper provinceMapper;

    @Autowired
    CityMapper cityMapper;

    @Autowired
    CountryMapper countryMapper;

    @Autowired
    DistrictMapper districtMapper;

    @Autowired
    ProvinceRepository provinceRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    CountryRepository countryRepository;

    @Override
    public List<GeolocationDto> getGeolocationByType(GeolocationType type, String id) {
        switch (type) {
            case country:
                List<Country> countries = countryRepository.findAll();
                return countryMapper.toDTOList(countries);
            case province:
                List<Province> provinces = provinceRepository.findByCountryIdOrderByName(id);
                return provinceMapper.toDTOList(provinces);
            case city:
                List<City> cities = cityRepository.findByProvinceIdOrderByName(id);
                return cityMapper.toDTOList(cities);
            case district:
                List<District> districts = districtRepository.findByCityIdOrderByName(id);
                return districtMapper.toDTOList(districts);
        }
        return null;
    }

}
