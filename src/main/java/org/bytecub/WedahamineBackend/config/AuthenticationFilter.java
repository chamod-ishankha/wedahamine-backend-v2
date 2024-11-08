package org.bytecub.WedahamineBackend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bytecub.WedahamineBackend.error.AuthorizationAlertException;
import org.bytecub.WedahamineBackend.service.impl.master.WedahamineUserDetailsService;
import org.bytecub.WedahamineBackend.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;
    private final WedahamineUserDetailsService userDetailsService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;


    public AuthenticationFilter(JWTUtils jwtUtils, WedahamineUserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader("Authorization");
            final String jwtToken;
            final String userEmail;
            if (authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            jwtToken = authHeader.substring(7);
            userEmail = jwtUtils.extractUsername(jwtToken);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    securityContext.setAuthentication(token);
                    SecurityContextHolder.setContext(securityContext);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            resolver.resolveException(request, response, null, new AuthorizationAlertException(e.getMessage(), "error", "unauthorized"));
        }
    }
}
