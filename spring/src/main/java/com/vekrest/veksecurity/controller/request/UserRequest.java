package com.vekrest.veksecurity.controller.request;

public record UserRequest(
        String username,
        String password
) {
}
