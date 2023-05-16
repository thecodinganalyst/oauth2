package com.thecodinganalyst.oauth2.repository;

import com.thecodinganalyst.oauth2.MongoDBTestContainerConfig;
import com.thecodinganalyst.oauth2.security.model.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
@ContextConfiguration(classes = MongoDBTestContainerConfig.class)
class AppUserRepositoryTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    AppUserRepository appUserRepository;

    @Test
    void givenUserExists_whenFindByUsername_thenGetUser() {
        AppUser appUser = new AppUser("user1", "password");
        mongoTemplate.save(appUser);

        Optional<AppUser> user = appUserRepository.findByUsername("user1");
        assertTrue(user.isPresent());
        assertThat(user.get().getUsername(), is("user1"));
        assertThat(user.get().getPassword(), is("password"));
    }

    @Test
    void givenNonExistingUser_whenFindByUsername_thenReturnNotPresent(){
        Optional<AppUser> user = appUserRepository.findByUsername("something");
        assertTrue(user.isEmpty());
    }
}