package org.socialnetwork.codebase.models;



import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@Suite
@SelectClasses({
        UserModelTest.class,
        RelationModelTest.class,
})
public class ModelTest {
}
