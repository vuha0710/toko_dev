package io.konekto.backoffice.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class BusinessProfileDto {

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
