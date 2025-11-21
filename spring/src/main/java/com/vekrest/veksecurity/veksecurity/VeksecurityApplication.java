package com.vekrest.veksecurity.veksecurity;

import com.vekrest.veksecurity.entity.User;
import com.vekrest.veksecurity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableMethodSecurity
@SpringBootApplication
public class VeksecurityApplication implements CommandLineRunner {
	private static final Logger LOG = LoggerFactory.getLogger(VeksecurityApplication.class);

	private final PasswordEncoder encoder;
	private final UserRepository userRepository;

	public VeksecurityApplication(PasswordEncoder encoder, UserRepository userRepository) {
		this.encoder = encoder;
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(VeksecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("VEKREST -> VEKSECURITY - INICIALIZADO COM SUCESSO!");

		User system = new User(
				"1",
				"sistema",
				"123456"
		);
		userRepository.save(system);

		LOG.info("Usuário sistema salvo com sucesso!");
	}
}
