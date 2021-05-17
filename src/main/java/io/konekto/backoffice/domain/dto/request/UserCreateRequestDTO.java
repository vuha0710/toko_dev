package io.konekto.backoffice.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Setter
@Getter
public class UserCreateRequestDTO {

    private Long id;

    private String name;

    private String password;

    @NotBlank
    @Email
    private String email;

    private boolean activated;

    private Set<String> authorities;
}
