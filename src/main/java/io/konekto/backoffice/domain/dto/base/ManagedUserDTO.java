package io.konekto.backoffice.domain.dto.base;

import io.konekto.backoffice.domain.BoUser;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ManagedUserDTO extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    public ManagedUserDTO(BoUser boUser) {
        super(boUser);
    }

}
