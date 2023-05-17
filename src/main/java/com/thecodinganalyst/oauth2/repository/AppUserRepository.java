package com.thecodinganalyst.oauth2.repository;

import com.thecodinganalyst.oauth2.security.user.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends MongoRepository<AppUser, String>{
    Optional<AppUser> findByUsername(String username);
}
