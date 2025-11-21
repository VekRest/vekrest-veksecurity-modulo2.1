package com.vekrest.veksecurity;

import com.vekrest.repository.UserRepository;
import com.vekrest.veksecurity.repository.client.UserRepositoryWithMongodb;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
class VeksecurityApplicationTests {
	@MockitoBean
	private MongoTemplate mongoTemplate;

	@MockitoBean
	private UserRepository userRepositoryInterface;

	@MockitoBean
	private UserRepositoryWithMongodb userRepositoryWithMongodb;

	@MockitoBean
	private GridFsTemplate gridFsTemplate;

	@Mock
	private MongoConverter mongoConverter;

	@Test
	void contextLoads() {}
}