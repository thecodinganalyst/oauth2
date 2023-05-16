package com.thecodinganalyst.oauth2.repository;

import com.thecodinganalyst.oauth2.MongoDBTestContainerConfig;
import com.thecodinganalyst.oauth2.security.model.AppOAuth2User;
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
class AppOAuth2UserRepositoryTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    AppOAuth2UserRepository appOAuth2UserRepository;

    @Test
    void givenUserExists_whenFindByUsernameAndProvider_thenReturnUser() {
        AppOAuth2User appOAuth2User = new AppOAuth2User("user1@gmail.com", "google");
        mongoTemplate.save(appOAuth2User);

        Optional<AppOAuth2User> user = appOAuth2UserRepository.findByUsernameAndProvider("user1@gmail.com", "google");
        assertTrue(user.isPresent());
        assertThat(user.get().getUsername(), is("user1@gmail.com"));
        assertThat(user.get().getProvider(), is("google"));
    }
}