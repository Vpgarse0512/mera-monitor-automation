package org.runner;

import io.cucumber.testng.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


@CucumberOptions(
        tags = "",
        features = "src/test/java/org/featureFiles/WebUser.feature",
        glue = {"test.java.org.myStepdefs"},
        monochrome = true,
        dryRun = false,
        plugin = {})
public class Runner extends AbstractTestNGCucumberTests {

}

