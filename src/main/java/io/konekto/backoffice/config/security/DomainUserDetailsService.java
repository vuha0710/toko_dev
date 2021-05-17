package io.konekto.backoffice.config.security;

import io.konekto.backoffice.domain.BoUser;
import io.konekto.backoffice.exception.BadRequestException;
import io.konekto.backoffice.repository.BoUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final BoUserRepository boUserRepository;

    public DomainUserDetailsService(BoUserRepository boUserRepository) {
        this.boUserRepository = boUserRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {
        log.debug("Authenticating {}", email);

            return boUserRepository.findOneWithAuthoritiesByEmailIgnoreCase(email)
                .map(user -> createSpringSecurityUser(email, user))
                .orElseThrow(() -> new UsernameNotFoundException("BoUser with email " + email + " was not found in the database"));

    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, BoUser boUser) {
        if (!boUser.isActivated()) {
            throw new BadRequestException("BoUser " + lowercaseLogin + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = boUser.getAuthorities().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
            .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(boUser.getEmail(),
            boUser.getPassword(),
            grantedAuthorities);
    }
}
