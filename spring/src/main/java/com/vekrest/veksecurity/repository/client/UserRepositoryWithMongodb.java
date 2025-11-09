package com.vekrest.veksecurity.repository.client;

import com.vekrest.veksecurity.repository.orm.UserOrm;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryWithMongodb extends MongoRepository<UserOrm, String> {
    Optional<UserOrm> findByUsername(String username);
}
