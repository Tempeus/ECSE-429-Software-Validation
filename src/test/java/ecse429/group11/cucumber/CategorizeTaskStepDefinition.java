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
    public static int todoID = 0;

    @Given("the todo manager API server is already running")
    public void theTodoManagerAPIServerIsAlreadyRunning(){
        TodoInstance.runApplication();
    }

    //Scenario Outline: Normal Flow

    @Given("a todo with the title {string}, done status {string} and description {string}")
    public void aTodoWithTheTitleDoneStatusAndDescription(String title, String doneStatus, String description){
        JSONObject todo = new JSONObject();
        todo.put("title", title);
        todo.put("doneStatus", doneStatus);
        todo.put("description", description);

        String validID = "/todos";
        try {
            TodoInstance.post(validID,todo.toString());
        } catch (IOException e) {
            error = true;
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            error = true;
        }
    }

    @When("the user requests to give the task {string} with a priority {string}")
    public void theUserRequestsToGiveTheTaskWithAPriority(String title, String priority){
        try {
            JSONObject todos = TodoInstance.send("GET","/todos");
        } catch (IOException e) {
            error = true;
        }
        //todo: Get the todo by name and id and set it as the local variable
        todoID = 0;
        json.put("categories", priority);

    }

    @Then("the task {string} will be assigned with the category {string}")
    public void theTaskWillBeAssignedWithTheCategory(String title, String priority){
        try {
            TodoInstance.post("/todos/" + todoID + "/categories", json.toString());
        } catch (IOException e) {
            error = true;
        }
        //todo add assert

    }

    //Scenario Outline: Alternative Flow

    @Given("a todo with the title {string}, done status {string}, description {string} and category {string}")
    public void aTodoWithTheTitleDoneStatusDescriptionAndCategory(String title, String doneStatus, String description, String priority){
        JSONObject todo = new JSONObject();
        todo.put("title", title);
        todo.put("doneStatus", doneStatus);
        todo.put("description", description);
        todo.put("category", priority);

        String validID = "/todos";
        try {
            TodoInstance.post(validID,todo.toString());
        } catch (IOException e) {
            error = true;
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            error = true;
        }
    }

    @When("user request to update the category of {string} to {string}")
    public void userRequestToUpdateTheCategoryOfFromTo(String title, String newPriority){
        try {
            JSONObject todos = TodoInstance.send("GET","/todos");
        } catch (IOException e) {
            error = true;
        }

        //todo: Get the todo by name and id and set it as the local variable
        todoID = 0;
        json.put("categories", newPriority);
    }

    @Then("task {string} will be assigned with a new category of {string}")
    public void taskWillBeAssignedWithANewCategoryOf(String title, String newPriority){
        try {
            TodoInstance.post("/todos/" + todoID + "/categories", json.toString());
        } catch (IOException e) {
            error = true;
        }
        //todo add assert
    }

    //Scenario Outline: Error Flow

    @When("user request to categorize a todo with title {string} with {string}")
    public void userRequestToCategorizeATodoWithTitleWith(String fakeTitle, String priority){
        json.put("category", priority);

        TodoInstance.send("GET", "/todos");

        //todo: Find the fake Title in the Array, realize you cant, using while loop
        error = true;
    }

    @Then("system will output an error")
    public void systemWillOutputAnError(){
        assertEquals(true, error);
    }

    @After
    public void shutdown(){
        TodoInstance.killInstance();
        error = false;
        todoID = 0;
    }
}

