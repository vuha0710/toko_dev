package io.konekto.backoffice.controller;

import io.konekto.backoffice.config.security.AuthoritiesConstants;
import io.konekto.backoffice.domain.BoUser;
import io.konekto.backoffice.domain.dto.base.UserDTO;
import io.konekto.backoffice.domain.dto.request.ChangePassRequestDTO;
import io.konekto.backoffice.domain.dto.request.UserCreateRequestDTO;
import io.konekto.backoffice.domain.dto.request.UserRequestDTO;
import io.konekto.backoffice.exception.BadRequestException;
import io.konekto.backoffice.repository.BoUserRepository;
import io.konekto.backoffice.service.BoUserService;
import io.konekto.backoffice.util.PaginationUtil;
import io.konekto.backoffice.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    BoUserService boUserService;

    @Autowired
    BoUserRepository boUserRepository;

    @PostMapping("/users")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<BoUser> createUser(@Valid @RequestBody UserCreateRequestDTO userDTO) throws URISyntaxException {

        if (userDTO.getId() != null) {
            throw new BadRequestException("A new user cannot already have an ID", "userManagement", "idexists");
        } else if (boUserRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new BadRequestException("Email Already Used Exception");
        } else {
            BoUser newBoUser = boUserService.createUser(userDTO);
            return ResponseEntity.ok(newBoUser);
        }
    }

    @PutMapping("/users")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserRequestDTO userDTO) {
        Optional<BoUser> existingUser = boUserRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new BadRequestException("Email Already Used Exception");
        }
        existingUser = boUserRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new BadRequestException("Login Already Used Exception");
        }
        boUserService.updateUser(userDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        final Page<UserDTO> page = boUserService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/users/authorities")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public List<String> getAuthorities() {
        return boUserService.getAuthorities();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ResponseUtil.wrapOrNotFound(
            boUserService.getUserWithAuthorities(id)
                .map(UserDTO::new));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boUserService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/change-password")
    public ResponseEntity<Void> changePassMember(@Valid @RequestBody ChangePassRequestDTO requestDTO) {
        boUserService.changePasswordMember(requestDTO);
        return ResponseEntity.ok().build();
    }
}
