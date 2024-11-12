package org.socialnetwork.codebase.service;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        UserServiceTest.class,
})
public class ServiceTest {
}
