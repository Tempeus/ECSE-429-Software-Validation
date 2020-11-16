package ecse429.group11.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class ChangeDescriptionStepDefinition {
    @Given("{string} is the description of the task")
    public void isTheDescriptionOfTheTask(String arg0) {
    }

    @Then("the task description will be changed to <description>")
    public void theTaskDescriptionWillBeChangedToDescription() {
    }

    @And("task is non-existent")
    public void taskIsNonExistent() {
    }

    @Then("an error {int} message will be displayed")
    public void anErrorMessageWillBeDisplayed(int arg0) {
    }
}
