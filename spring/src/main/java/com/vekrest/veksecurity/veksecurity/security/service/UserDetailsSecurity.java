package com.vekrest.veksecurity.veksecurity.security.service;

import com.vekrest.veksecurity.entity.User;
import com.vekrest.veksecurity.repository.UserRepository;
import com.vekrest.veksecurity.veksecurity.security.dto.AuthUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsSecurity implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsSecurity.class);

    private final UserRepository repository;

    public UserDetailsSecurity(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        LOG.info("Loading user details for username: {}", username);
        return new AuthUserDetails(user);
    }
}
