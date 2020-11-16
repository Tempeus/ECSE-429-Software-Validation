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

public class CategorizeTaskStepDefinition {

    public static JSONObject json = null;
    public static boolean error = false;

    @Given("the todo manager API server is already running")
    public void theTodoManagerAPIServerIsAlreadyRunning(){
        TodoInstance.runApplication();
    }

    @And("HIGH, MEDIUM and LOW categories are registered in the todo manager API")
    public void HIGHMEDandLOWCategoriesAreRegisteredInTheTodoManagerAPI(){

    }

    //Scenario Outline: Normal Flow

    @Given("a todo with the title {string}, done status {string} and description {string}")
    public void aTodoWithTheTitleDoneStatusAndDescription(String title, String doneStatus, String description){

    }

    @When("the user requests to give the task {string} with a priority {string}")
    public void theUserRequestsToGiveTheTaskWithAPriority(String title, String priority){

    }

    @Then("the task {string} will be assigned with the category {string}")
    public void theTaskWillBeAssignedWithTheCategory(String title, String priority){

    }

    //Scenario Outline: Alternative Flow

    @Given("a todo with the title {string}, done status {string}, description {string} and category {string}")
    public void aTodoWithTheTitleDoneStatusDescriptionAndCategory(String title, String doneStatus, String description, String priority){

    }

    @When("user request to update the category of {string} from {string} to {string}")
    public void userRequestToUpdateTheCategoryOfFromTo(String title, String priority, String newPriority){

    }

    @Then("task {string} will be assigned with a new category of {string}")
    public void taskWillBeAssignedWithANewCategoryOf(String title, String newPriority){

    }

    //Scenario Outline: Error Flow

    @Given("no todo is registered in the API server")
    public void NoTodoIsRegisteredInTheAPIServer(){

    }

    @When("user request to categorize a todo with title {string} with {string}")
    public void userRequestToCategorizeATodoWithTitleWith(String fakeTitle, String priority){

    }

    @Then("system will output an error")
    public void systemWillOutputAnError(){

    }

    @After
    public void shutdown(){
        TodoInstance.killInstance();
        error = false;
    }
}

