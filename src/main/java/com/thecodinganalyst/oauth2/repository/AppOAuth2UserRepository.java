package com.thecodinganalyst.oauth2.repository;

import com.thecodinganalyst.oauth2.security.model.AppOAuth2User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AppOAuth2UserRepository extends MongoRepository<AppOAuth2User, String> {
    Optional<AppOAuth2User> findByUsernameAndProvider(String username, String provider);
}
