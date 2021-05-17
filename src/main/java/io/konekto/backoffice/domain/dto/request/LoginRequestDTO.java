package io.konekto.backoffice.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
public class LoginRequestDTO {

    @NotNull
    @Size(min = 1, max = 50)
    @Email
    private String email;

    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    private Boolean rememberMe;

    @Override
    public String toString() {
        return "LoginVM{" +
            "email='" + email + '\'' +
            ", rememberMe=" + rememberMe +
            '}';
    }
}
