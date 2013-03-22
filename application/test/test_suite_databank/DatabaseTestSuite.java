package test_suite_databank;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import content_test_suite.ErrorPageTest;



@RunWith(Suite.class)
@SuiteClasses({ ErrorPageTest.class })
public class DatabaseTestSuite {

}
