package io.konekto.backoffice.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class AccountUpdateRequestDTO {

    @NotEmpty
    private String name;
}
