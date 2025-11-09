package com.vekrest.repository;

import com.vekrest.entity.User;

public interface UserRepository {
    User save(User user);

    User findByUsername(String username);
}
