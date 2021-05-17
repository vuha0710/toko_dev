package io.konekto.backoffice.repository;

import io.konekto.backoffice.domain.BoUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoUserRepository extends JpaRepository<BoUser, Long> {

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<BoUser> findOneByEmailIgnoreCase(String email);

    @EntityGraph(attributePaths = "authorities")
    Optional<BoUser> findOneWithAuthoritiesById(Long id);

    @EntityGraph(attributePaths = "authorities")
    Optional<BoUser> findOneWithAuthoritiesByEmailIgnoreCase(String email);

}
