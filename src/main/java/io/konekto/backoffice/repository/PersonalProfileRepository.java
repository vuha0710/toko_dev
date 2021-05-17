package io.konekto.backoffice.repository;

import io.konekto.backoffice.domain.PersonalProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalProfileRepository extends JpaRepository<PersonalProfile, String> {

}
