package com.wsd.ecommerce.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorConfig implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
        } else {
            return Optional.of("SYSTEM/NOT APPLICABLE");
        }
    }
}
