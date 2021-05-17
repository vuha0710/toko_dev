package io.konekto.backoffice.repository;

import io.konekto.backoffice.domain.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VillageRepository extends JpaRepository<Village, String> {

    List<Village> findByDistrictIdOrderByName(String districtId);

}
