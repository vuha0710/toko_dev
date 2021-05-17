package io.konekto.backoffice.service;

import io.konekto.backoffice.config.security.SecurityUtils;
import io.konekto.backoffice.domain.BoAuthority;
import io.konekto.backoffice.domain.BoUser;
import io.konekto.backoffice.domain.dto.base.UserDTO;
import io.konekto.backoffice.domain.dto.request.AccountUpdateRequestDTO;
import io.konekto.backoffice.domain.dto.request.ChangePassRequestDTO;
import io.konekto.backoffice.domain.dto.request.UserCreateRequestDTO;
import io.konekto.backoffice.domain.dto.request.UserRequestDTO;
import io.konekto.backoffice.exception.BadRequestException;
import io.konekto.backoffice.repository.BoAuthorityRepository;
import io.konekto.backoffice.repository.BoUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BoUserService {

    private final Logger log = LoggerFactory.getLogger(BoUserService.class);

    private final BoUserRepository boUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final BoAuthorityRepository boAuthorityRepository;

    public BoUserService(BoUserRepository boUserRepository, PasswordEncoder passwordEncoder, BoAuthorityRepository boAuthorityRepository) {
        this.boUserRepository = boUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.boAuthorityRepository = boAuthorityRepository;
    }

    public BoUser createUser(UserCreateRequestDTO userDTO) {
        BoUser boUser = new BoUser();
        boUser.setName(userDTO.getName());
        if (userDTO.getEmail() != null) {
            boUser.setEmail(userDTO.getEmail().toLowerCase());
        }
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        boUser.setPassword(encryptedPassword);
        boUser.setActivated(userDTO.isActivated());
        if (userDTO.getAuthorities() != null) {
            Set<BoAuthority> authorities = userDTO.getAuthorities().stream()
                .map(boAuthorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            boUser.setAuthorities(authorities);
        }
        boUserRepository.save(boUser);
        log.debug("Created Information for BoUser: {}", boUser);
        return boUser;
    }

    public void updateUser(String name, String email, String langKey, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(boUserRepository::findOneByEmailIgnoreCase)
            .ifPresent(user -> {
                user.setName(name);
                if (email != null) {
	                user.setEmail(email.toLowerCase());
                }
                user.setImageUrl(imageUrl);
                log.debug("Changed Information for BoUser: {}", user);
            });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public Optional<UserDTO> updateUser(UserRequestDTO userDTO) {
        return Optional.of(boUserRepository
            .findById(userDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                user.setName(userDTO.getName());
                if (userDTO.getEmail() != null) {
                    user.setEmail(userDTO.getEmail().toLowerCase());
                }
                user.setActivated(userDTO.isActivated());
                Set<BoAuthority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userDTO.getAuthorities().stream()
                    .map(boAuthorityRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(managedAuthorities::add);
                log.debug("Changed Information for BoUser: {}", user);
                return user;
            })
            .map(UserDTO::new);
    }

    public void deleteUser(Long id) {
        boUserRepository.findOneWithAuthoritiesById(id).ifPresent(user -> {
            boUserRepository.delete(user);
            log.debug("Deleted BoUser: {}", user);
        });
    }

    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(boUserRepository::findOneByEmailIgnoreCase)
            .ifPresent(user -> {
                String currentEncryptedPassword = user.getPassword();
                if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                    throw new BadRequestException("Invalid password");
                }
                String encryptedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encryptedPassword);
                log.debug("Changed password for BoUser: {}", user);
            });
    }

    public void updateAccount(AccountUpdateRequestDTO requestDTO) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(boUserRepository::findOneByEmailIgnoreCase)
            .ifPresent(user -> {
                user.setName(requestDTO.getName());
                boUserRepository.save(user);
            });
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return boUserRepository.findAll(pageable).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<BoUser> getUserWithAuthorities(Long id) {
        return boUserRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public Optional<BoUser> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(boUserRepository::findOneByEmailIgnoreCase);
    }

    public List<String> getAuthorities() {
        return boAuthorityRepository.findAll().stream().map(BoAuthority::getName).collect(Collectors.toList());
    }


    public void changePasswordMember(ChangePassRequestDTO requestDTO) {
        Optional<BoUser> userMember = boUserRepository.findById(requestDTO.getUserId());
        if (!userMember.isPresent()) {
            throw new BadRequestException("User is not found");
        }
        String encryptedPassword = passwordEncoder.encode(requestDTO.getPassword());
        userMember.get().setPassword(encryptedPassword);
        boUserRepository.save(userMember.get());
    }
}
