package org.bytecub.WedahamineBackend.config.aduit;

import jakarta.servlet.http.HttpServletRequest;
import org.bytecub.WedahamineBackend.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

import static org.bytecub.WedahamineBackend.constants.Common.TOKEN_TYPE;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditingConfig {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JWTUtils jwtUtil;

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            // Extract the token from the Authorization header
            String authorizationHeader = request.getHeader("Authorization");
            String username;

            if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_TYPE)) {
                String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
                username = jwtUtil.extractUsername(token);

                // Return the username if token is valid
                if (username != null) {
                    return Optional.of(username);
                }
            }

            // Default to "System" if no token is provided or token is invalid
            return Optional.of("System");
        };
    }
}
