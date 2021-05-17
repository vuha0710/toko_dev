package io.konekto.backoffice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * BusinessProfile entity represents user's business profile.
 */
@Entity
@Table(name = "business_profile")
@Getter
@Setter
public class BusinessProfile {

    @Id
    private String userId;

    private String name;
    private String avatar;
    private String entityType;
    private String type;
    private String subType;
    private String mobile;
    private String phone;
    private String email;
    private String province;
    private String city;
    private String district;
    private String village;
    private String postalCode;
    private String address;
    private String website;
    private String language;
    private String country;
    private String verifyStatus;
    private String businessCardName;
    private String businessCardType;
    private String businessCardTemplate;
    private Long businessCardColor;
    private Date createdAt;
    private Date updatedAt;
    private String businessId;
}
