package com.vekrest.veksecurity.controller.adapter;

import com.vekrest.entity.User;
import com.vekrest.veksecurity.controller.request.UserRequest;
import java.util.UUID;

public class UserControllerAdapter {
    private UserControllerAdapter() {
    }

    public static User cast(UserRequest request) {
        return new User(
                UUID.randomUUID().toString(),
                request.username(),
                request.password()
        );
    }
}