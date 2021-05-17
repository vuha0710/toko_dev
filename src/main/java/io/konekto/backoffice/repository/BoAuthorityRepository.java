package io.konekto.backoffice.repository;

import io.konekto.backoffice.domain.BoAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoAuthorityRepository extends JpaRepository<BoAuthority, String> {
}
