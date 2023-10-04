package bdd.runner;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-reports/report.html",
                "json:target/cucumber-reports/CucumberTests.json",
                "testng:target/cucumber-reports/CucumberTests.xml"},
        monochrome = true,
        glue = {"business.cucumber.steps", "business.cucumber.hooks"},
        features = "src/test/java/bdd/features/"
)
public class CucumberTestRunner {
}
