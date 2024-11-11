package org.socialnetwork.codebase.repository;


import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        PersonRepositoryTest.class,
        RelationRepositoryTest.class,
})
public class RepositoryTest {
}
