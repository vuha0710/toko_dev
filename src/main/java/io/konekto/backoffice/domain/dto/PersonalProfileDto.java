package io.konekto.backoffice.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PersonalProfileDto {

    private String userId;
    private String userMobile;
    private String referralCode;
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
