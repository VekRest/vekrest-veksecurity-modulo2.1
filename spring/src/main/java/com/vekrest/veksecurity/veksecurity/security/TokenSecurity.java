package com.vekrest.veksecurity.veksecurity.security;

import com.vekrest.veksecurity.entity.Token;
import com.vekrest.veksecurity.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class TokenSecurity {
    private static final Logger LOG = LoggerFactory.getLogger(TokenSecurity.class);

    private final JwtSecurity jwtSecurity;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public TokenSecurity(
            JwtSecurity jwtSecurity,
            UserDetailsService userDetailsService,
            AuthenticationManager authenticationManager) {
        this.jwtSecurity = jwtSecurity;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    public Token generateToken(User user) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user.username(), user.password());
        authenticationManager.authenticate(authToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.username());

        LOG.info("Generating token for user: {}", user.username());
        return new Token(jwtSecurity.generateToken(userDetails));
    }

}
