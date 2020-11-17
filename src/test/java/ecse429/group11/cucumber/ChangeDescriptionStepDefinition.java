package ecse429.group11.cucumber;


import ecse429.group11.restAPI.TodoInstance;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ChangeDescriptionStepDefinition {

    public static JSONObject json = null;
    public static boolean error = false;

    @Given("{string} is the new description of the task")
    public void isTheNewDescriptionOfTheTask(String description) {
        json.put("description", description);
    }

    @And("{string} is the given task id to be changed")
    public void isTheGivenTitleIdToBeChanged(String taskid) {
        json.put("taskid", taskid);
    }

    @When("the user posts a request to the server")
    public void theUserPostsADescriptionRequestToTheServer(){
        String validID = "/todos/" + json.get("taskid").toString();
        try {
            TodoInstance.post(validID,json.get("description").toString());
        } catch (IOException e) {
            error = true;
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("the task description will be changed to {string}")
    public void theTaskDescriptionWillBeChangedTo(String arg0) {
        JSONObject response = null;
        try {
            response = TodoInstance.send("GET", "/todos/" + json.get("id").toString() );
        } catch (IOException e) {
            error = true;
        }

        assertEquals(json.get("description").toString(), response.getJSONArray("todos").getJSONObject(0).get("description").toString());
        assertEquals(false, error);
    }

    @And("is related to projects with id {string}")
    public void isRelatedToProjectsWithIdTasksofid(String arg0) {
        json.put("categories", arg0);
    }

    @And("{string} is the given id of the non-existent task")
    public void isTheGivenIdOfTheNonExistentTask(String arg0) {
        json.put("nonid", arg0);
    }

    @Then("a {string} error message {int} will be displayed")
    public void aErrorMessageWillBeDisplayed(String arg0, int arg1) {
        JSONObject response = null;

        try {
            response = TodoInstance.send("GET", "/todos");
        } catch (IOException e) {
            error = true;
        }

        assertEquals(404, arg0);
    }

    @After
    public void shutdown(){
        TodoInstance.killInstance();
        error = false;
    }
}

