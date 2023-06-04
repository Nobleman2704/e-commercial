package com.example.ecommercial.domain.listener;

import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component(value = "auditorAware")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditingAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication==null){
            return Optional.of("bot");
        }
        return Optional.of(authentication.getName());
    }
}
