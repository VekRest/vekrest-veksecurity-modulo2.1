package com.vekrest.veksecurity.repository.adapter;

import com.vekrest.entity.User;
import com.vekrest.veksecurity.repository.orm.UserOrm;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserRepositoryAdapter {
    private UserRepositoryAdapter() {
    }

    public static User cast(UserOrm orm, PasswordEncoder passwordEncoder) {
        return new User(
                orm.id(),
                orm.username(),
                passwordEncoder.encode(orm.password())
        );
    }

    public static UserOrm cast(User user) {
        return new UserOrm(
                user.id(),
                user.username(),
                user.password()
        );
    }

}
