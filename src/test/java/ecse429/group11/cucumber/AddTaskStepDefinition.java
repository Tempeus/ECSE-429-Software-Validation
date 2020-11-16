package java.ecse429.group11.cucumber;

import java.ecse429.group11.restAPI.TodoInstance;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AddTaskStepDefinition {
    @Given("there exists a todo list in the system with title {string}")
    public void thereExistsATodoListInTheSystemWithTitle(String listTitle) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("I add a new task with the title {string} to the todo list {string}")
    public void iAddANewTaskWithTheTitleToTheTodoList(String taskTitle, String listTitle) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("there will be a new task with title {string} in the todo list {string}")
    public void thereWillBeANewTaskWithTitleInTheTodoList(String taskTitle, String listTitle) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Given("there does not exist a todo list with title {string}")
    public void thereDoesNotExistATodoListWithTitle(String listTitle) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("the system will inform the user that the todo list {string} is non-existent")
    public void theSystemWillInformTheUserThatTheTodoListIsNonExistent(String listTitle) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
}
