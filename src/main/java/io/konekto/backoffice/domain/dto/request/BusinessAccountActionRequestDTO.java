package io.konekto.backoffice.domain.dto.request;

import io.konekto.backoffice.domain.enumration.VerifyStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusinessAccountActionRequestDTO {

    String userId;
    VerifyStatusEnum verifyStatus;

}
