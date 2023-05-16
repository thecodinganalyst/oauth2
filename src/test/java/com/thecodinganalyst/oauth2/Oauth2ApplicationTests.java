package com.thecodinganalyst.oauth2;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ContextConfiguration(classes = MongoDBTestContainerConfig.class)
class Oauth2ApplicationTests {

    @Test
    void contextLoads() {
    }

}
