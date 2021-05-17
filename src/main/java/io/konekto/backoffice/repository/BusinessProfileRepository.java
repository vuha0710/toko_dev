package io.konekto.backoffice.repository;

import io.konekto.backoffice.domain.BusinessProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessProfileRepository extends JpaRepository<BusinessProfile, String> {

}
