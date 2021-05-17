package io.konekto.backoffice.repository;

import io.konekto.backoffice.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, String> {

    List<City> findByProvinceIdOrderByName(String id);

}
