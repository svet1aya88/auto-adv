package cucumber;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-reports/report.html",
                "json:target/cucumber-reports/CucumberTests.json",
                "testng:target/cucumber-reports/CucumberTests.xml"},
        monochrome = true,
        glue = "src.main.java.business.cucumber.steps",
        features = "src.main.java.business.cucumber.features"
)
public class CucumberTestRunner {
}
