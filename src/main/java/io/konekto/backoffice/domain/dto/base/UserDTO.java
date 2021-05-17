package io.konekto.backoffice.domain.dto.base;

import io.konekto.backoffice.domain.BoAuthority;
import io.konekto.backoffice.domain.BoUser;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
public class UserDTO {

    private Long id;

    private String name;

    @NotBlank
    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    @Size(min = 2, max = 10)
    private String langKey;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Set<String> authorities;

    public UserDTO(BoUser boUser) {
        this.id = boUser.getId();
        this.name = boUser.getName();
        this.email = boUser.getEmail();
        this.activated = boUser.isActivated();
        this.imageUrl = boUser.getImageUrl();
        this.createdBy = boUser.getCreatedBy();
        this.createdDate = boUser.getCreatedDate();
        this.lastModifiedBy = boUser.getLastModifiedBy();
        this.lastModifiedDate = boUser.getLastModifiedDate();
        this.authorities = boUser.getAuthorities().stream()
            .map(BoAuthority::getName)
            .collect(Collectors.toSet());
    }
}
