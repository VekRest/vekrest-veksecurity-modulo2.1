package com.vekrest.veksecurity.repository;

import com.vekrest.veksecurity.entity.User;

public interface UserRepository {
    User save(User user);

    User findByUsername(String username);
}
