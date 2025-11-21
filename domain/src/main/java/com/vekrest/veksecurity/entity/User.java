package com.vekrest.veksecurity.entity;

public record User(
        String id,
        String username,
        String password
) {
}