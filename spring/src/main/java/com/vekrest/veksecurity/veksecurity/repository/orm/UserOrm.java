package com.vekrest.veksecurity.veksecurity.repository.orm;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public record UserOrm(
        @Id
        String id,
        @Indexed
        String username,
        String password
) {
}
