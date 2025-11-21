package com.vekrest.veksecurity.controller;

import com.vekrest.entity.Token;
import com.vekrest.veksecurity.controller.adapter.UserControllerAdapter;
import com.vekrest.veksecurity.controller.request.UserRequest;
import com.vekrest.veksecurity.controller.response.AuthResponse;
import com.vekrest.veksecurity.security.TokenSecurity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vekrest/veksecurity")
public class AuthController {
    private final TokenSecurity tokenSecurity;

    public AuthController(
            TokenSecurity tokenSecurity
    ) {
        this.tokenSecurity = tokenSecurity;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserRequest request) {
        Token token = tokenSecurity.generateToken(UserControllerAdapter.cast(request));
        return new AuthResponse(token.value());
    }
}