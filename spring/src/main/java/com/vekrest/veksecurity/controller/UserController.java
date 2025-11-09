package com.vekrest.veksecurity.controller;

import com.vekrest.entity.User;
import com.vekrest.repository.UserRepository;
import com.vekrest.veksecurity.controller.adapter.UserControllerAdapter;
import com.vekrest.veksecurity.controller.request.UserRequest;
import com.vekrest.veksecurity.controller.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vekrest")
public class UserController {
    private final UserRepository repository;

    public UserController(
            UserRepository repository
    ) {
        this.repository = repository;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/security/user/save")
    public UserResponse save(@RequestBody UserRequest request) {
        User save = repository.save(UserControllerAdapter.cast(request));
        return new UserResponse(
                save.id(),
                save.username()
        );
    }
}