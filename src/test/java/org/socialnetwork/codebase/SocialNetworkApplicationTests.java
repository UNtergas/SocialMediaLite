package org.socialnetwork.codebase;

import org.junit.jupiter.api.Test;

import org.junit.platform.suite.api.SelectClasses;

import org.junit.platform.suite.api.Suite;
import org.socialnetwork.codebase.models.ModelTest;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@SelectClasses({
        ModelTest.class
})
@SpringBootTest
class SocialNetworkApplicationTests {

    @Test
    void contextLoads() {
    }

}
