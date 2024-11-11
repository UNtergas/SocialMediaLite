package org.socialnetwork.codebase.service;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        PersonServiceTest.class,
})
public class ServiceTest {
}
