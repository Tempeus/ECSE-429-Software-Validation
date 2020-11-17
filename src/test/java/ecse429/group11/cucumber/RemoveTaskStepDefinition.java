package ecse429.group11.cucumber;

import ecse429.group11.restAPI.TodoInstance;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RemoveTaskStepDefinition {

    public static JSONObject json = null;
    public static boolean error = false;

    @Given("{string} is the id of the task")
    public void isTheIdOfTheTask(String arg0) {
        json.put("tasktodoid", arg0);
    }

    @And("{string} is the id of the to do list")
    public void isTheIdOfTheToDoList(String arg0) {
        json.put("todolistid", arg0);
    }

    @When("the user posts a request to the server to remove a task ")
    public void theUserPostsADeleteTaskRequestToTheServer(){
        String validID = "/projects/" + json.get("todolistid").toString();
        try {
            TodoInstance.delete(validID,json.get("tasktodoid").toString());
        } catch (IOException e) {
            error = true;
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("the to do list will no longer have the task")
    public void theToDoListWillNoLongerHaveTheTask() {
        JSONObject response = null;
        try {
            response = TodoInstance.send("GET", "/todos/" + json.get("tasktodoid").toString() );
        } catch (IOException e) {
            error = true;
        }

        assertEquals(null, response.getJSONArray("todos").getJSONObject(0).get("tasksof").toString());
        assertEquals(false, error);
    }

    @And("{string} is the id of the task category")
    public void isTheIdOfTheTaskCategory(String arg0) {
        json.put("categorytaskid", arg0);
    }

    @Given("{string} is the id of the non-existent task")
    public void isTheIdOfTheNonExistentTask(String arg0) {
        json.put("nontaskid", arg0);
    }

    @Then("a {string} error {int} message will be displayed")
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
