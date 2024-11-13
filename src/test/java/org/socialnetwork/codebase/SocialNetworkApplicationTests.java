package org.socialnetwork.codebase;

import org.junit.jupiter.api.Test;

import org.junit.platform.suite.api.SelectClasses;

import org.junit.platform.suite.api.Suite;
import org.socialnetwork.codebase.models.ModelTest;
//import org.socialnetwork.codebase.repository.RepositoryTest;
import org.socialnetwork.codebase.service.ServiceTest;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@SelectClasses({
        ModelTest.class,
//        RepositoryTest.class, TODO: FIX THIS TEST
        ServiceTest.class,
})
@SpringBootTest
class SocialNetworkApplicationTests {

    @Test
    void contextLoads() {
    }

}
