package io.konekto.backoffice.repository;

import io.konekto.backoffice.domain.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, String> {

    List<District> findByCityIdOrderByName(String id);

}
