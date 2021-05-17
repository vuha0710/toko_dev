package io.konekto.backoffice.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ChangePassRequestDTO {

    @NotNull
    private Long userId;

    @NotEmpty
    private String password;
}
