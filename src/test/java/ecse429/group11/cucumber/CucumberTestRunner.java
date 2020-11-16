package ecse429.group11.cucumber;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = "pretty",
        features = "src/test/ecse429/group11/resources",
        glue = {"ecse429.group11.cucumber"})
public class CucumberTestRunner {
}
