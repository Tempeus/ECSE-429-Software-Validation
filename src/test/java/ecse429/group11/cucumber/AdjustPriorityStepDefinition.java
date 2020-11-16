package ecse429.group11.cucumber;

import java.ecse429.group11.restAPI.TodoInstance;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AdjustPriorityStepDefinition {

    @Given("HIGH, MEDIUM and LOW categories are registered in the todo manager API")
    public void highMEDIUMAndLOWCategoriesAreRegisteredInTheTodoManagerAPI() {
    }

    @Given("the task with title {string} exists")
    public void theTaskWithTitleExists(String title) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("the task has {string} priority")
    public void theTaskHasPriority(String old_priority) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("the student changes the task to {string} priority")
    public void theStudentChangesTheTaskToPriority(String new_priority) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("the priority of the task should be {string}")
    public void thePriorityOfTheTaskShouldBe(String new_priority) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("the task with title {string} does not exist")
    public void theTaskWithIdDoesNotExist(String title) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("the student changes the task with title {string} to {string} priority")
    public void theStudentChangesTheTaskWithIdToPriority(String title, String new_priority) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("the system shall inform the user that the task is non-existent")
    public void theSystemShallInformTheUserThatTheTaskIsNonExistent() {
    }
}
