package Runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/Features",
        glue = "StepDefinitions",
        tags = "@Sanity",
        plugin = {
                "pretty",
                "html:target/reports/cucumber.html",
                "json:target/reports/cucumber.json",
                "rerun:target/reports/rerun.txt",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }
)
public class TestRunner extends AbstractTestNGCucumberTests {
}