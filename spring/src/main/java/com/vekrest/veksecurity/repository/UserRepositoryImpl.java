package com.vekrest.veksecurity.repository;

import com.vekrest.entity.User;
import com.vekrest.repository.UserRepository;
import com.vekrest.veksecurity.repository.adapter.UserRepositoryAdapter;
import com.vekrest.veksecurity.repository.client.UserRepositoryWithMongodb;
import com.vekrest.veksecurity.repository.orm.UserOrm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private final PasswordEncoder encoder;
    private final UserRepositoryWithMongodb repository;

    public UserRepositoryImpl(
            PasswordEncoder encoder,
            UserRepositoryWithMongodb repository
    ) {
        this.encoder = encoder;
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        try {
            UserOrm orm = repository.save(UserRepositoryAdapter.cast(user));

            LOG.info("User saved with ID: {}", orm.id());
            return UserRepositoryAdapter.cast(orm, encoder);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public User findByUsername(String username) {
        try {
            Optional<UserOrm> optional = repository.findByUsername(username);
            if (optional.isEmpty()) {
                throw new UsernameNotFoundException("Usuario e/ou senha invalidos");
            }

            LOG.info("User found with username: {}", username);
            return UserRepositoryAdapter.cast(optional.get(), encoder);
        } catch (UsernameNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
