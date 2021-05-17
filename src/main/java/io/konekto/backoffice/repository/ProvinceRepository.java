package io.konekto.backoffice.repository;

import io.konekto.backoffice.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvinceRepository extends JpaRepository<Province, String> {

    List<Province> findByCountryIdOrderByName(String countryId);

}
