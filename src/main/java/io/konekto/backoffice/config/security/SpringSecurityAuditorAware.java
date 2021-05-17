package io.konekto.backoffice.config.security;

import io.konekto.backoffice.domain.constant.Constant;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentUserLogin().orElse(Constant.SYSTEM_ACCOUNT));
    }
}
