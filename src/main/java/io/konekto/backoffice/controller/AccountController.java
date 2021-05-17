package io.konekto.backoffice.controller;


import io.konekto.backoffice.config.security.SecurityUtils;
import io.konekto.backoffice.domain.BoUser;
import io.konekto.backoffice.domain.dto.base.ManagedUserDTO;
import io.konekto.backoffice.domain.dto.base.PasswordChangeDTO;
import io.konekto.backoffice.domain.dto.base.UserDTO;
import io.konekto.backoffice.domain.dto.request.AccountUpdateRequestDTO;
import io.konekto.backoffice.exception.BadRequestException;
import io.konekto.backoffice.repository.BoUserRepository;
import io.konekto.backoffice.service.BoUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AccountController {

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final BoUserRepository boUserRepository;

    private final BoUserService boUserService;

    public AccountController(BoUserRepository boUserRepository, BoUserService boUserService) {

        this.boUserRepository = boUserRepository;
        this.boUserService = boUserService;
    }

    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    @GetMapping("/account")
    public UserDTO getAccount() {
        return boUserService.getUserWithAuthorities()
            .map(UserDTO::new)
            .orElseThrow(() -> new AccountResourceException("BoUser could not be found"));
    }

    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<BoUser> existingUser = boUserRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getEmail().equalsIgnoreCase(userLogin))) {
            throw new BadRequestException("EmailAlreadyUsedException");
        }
        Optional<BoUser> user = boUserRepository.findOneByEmailIgnoreCase(userLogin);
        if (!user.isPresent()) {
            throw new AccountResourceException("BoUser could not be found");
        }
        boUserService.updateUser(userDTO.getName(), userDTO.getEmail(),
            userDTO.getLangKey(), userDTO.getImageUrl());
    }

    @PostMapping(path = "/account/change-password")
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO) {
        if (!checkPasswordLength(passwordChangeDTO.getNewPassword())) {
            throw new BadRequestException("InvalidPasswordException");
        }
        boUserService.changePassword(passwordChangeDTO.getCurrentPassword(), passwordChangeDTO.getNewPassword());
    }

    @PutMapping(path = "/account")
    public void updateAccount(@Valid @RequestBody AccountUpdateRequestDTO requestDTO) {
        boUserService.updateAccount(requestDTO);
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserDTO.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserDTO.PASSWORD_MAX_LENGTH;
    }
}
