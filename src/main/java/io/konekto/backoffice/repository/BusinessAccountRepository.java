package io.konekto.backoffice.repository;

import io.konekto.backoffice.domain.BusinessProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessAccountRepository extends JpaRepository<BusinessProfile, String> {

    @Query("SELECT bp, pp FROM BusinessProfile bp " +
            "JOIN PersonalProfile pp ON pp.userId = bp.userId " +
            "WHERE (:q is null or :q is '' or bp.name like %:q% OR pp.name like %:q%) " +
            "AND (:province is null or :province is '' or bp.province=:province OR pp.province=:province) " +
            "AND (:city is null or :city is '' or bp.city=:city OR pp.city=:city) " +
            "AND (:district is null or :district is '' or bp.district=:district OR pp.district=:district) " +
            "AND (:businessType is null or :businessType is '' or bp.type=:businessType) " +
            "AND (:subBusinessType is null or :subBusinessType is '' or bp.subType=:subBusinessType) " +
            "AND (:businessEntity is null or :businessEntity is '' or bp.entityType=:businessEntity)" +
            "AND (:verifyStatus is null or :verifyStatus is '' or bp.verifyStatus=:verifyStatus) " +
            "AND bp.verifyStatus is not null " +
            "order by bp.createdAt desc, pp.createdAt desc")
    Page<Object[]> getAccountProfile(
            @Param("q") String q,
            @Param("province") String province,
            @Param("city") String city,
            @Param("district") String district,
            @Param("businessType") String businessType,
            @Param("subBusinessType") String subBusinessType,
            @Param("businessEntity") String businessEntity,
            @Param("verifyStatus") String verifyStatus,
            Pageable pageable);

}
