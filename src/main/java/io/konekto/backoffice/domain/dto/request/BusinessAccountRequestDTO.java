package io.konekto.backoffice.domain.dto.request;

import io.konekto.backoffice.domain.enumration.VerifyStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusinessAccountRequestDTO {

    String q;
    String province;
    String city;
    String district;
    String businessType;
    String subBusinessType;
    String businessEntity;
    VerifyStatusEnum verifyStatus;

}
