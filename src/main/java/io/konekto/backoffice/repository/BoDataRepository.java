package io.konekto.backoffice.repository;

import io.konekto.backoffice.domain.BoData;
import io.konekto.backoffice.domain.enumration.DataKeyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoDataRepository extends JpaRepository<BoData, Long> {

    Optional<BoData> findByDataKey(DataKeyType dataKey);

    List<BoData> findByDataKeyIn(List<DataKeyType> dataKeys);

}
