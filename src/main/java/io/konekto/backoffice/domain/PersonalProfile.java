package io.konekto.backoffice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * PersonalProfile entity represents user's personal profile.
 */
@Entity
@Table(name = "personal_profile")
@Getter
@Setter
public class PersonalProfile {

    @Id
    private String userId;

    @Column(unique = true)
    private String userMobile;

    @Column(unique = true)
    private String referralCode;

    @Column(unique = true)
    private String referralLink;

    private String name;
    private String avatar;
    private String email;
    private String mobile;
    private String province;
    private String city;
    private String district;
    private String village;
    private String address;
    private String dob;
    private String postalCode;
    private String rt;
    private String rw;
    private String idCardNumber;
    private String idCardName;
    private String idCardPhoto;
    private String idCardSelfie;
    private String referrerId;
    private Date createdAt;
    private Date updatedAt;
    private String gender;
}
