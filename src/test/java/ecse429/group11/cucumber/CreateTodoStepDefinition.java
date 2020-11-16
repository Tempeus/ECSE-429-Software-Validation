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

public class CreateTodoStepDefinition {

    public static JSONObject json = null;
    public static boolean error = false;

    @Given("the Todo API server is running")
    public void the_Todo_API_server_is_running(){
        TodoInstance.runApplication();
        json = new JSONObject();
    }

    @Given("{string} is the title of the class")
    public void is_the_title_of_the_class(String title){
        json.put("title", title);
    }

    @Given("{string} is the description of the class")
    public void isTheDescriptionOfTheClass(String description){
        json.put("description", description);
    }

    @Given("{string} is the done status of the class")
    public void is_the_done_status_of_the_class(String doneStatus){
        json.put("doneStatus", doneStatus);
    }

    //Scenario Outline: Normal Flow

    @When("the user posts a request to the server")
    public void theUserPostsARequestToTheServer(){
        String validID = "/todos";
        try {
            TodoInstance.post(validID,json.toString());
        } catch (IOException e) {
            error = true;
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("a todo instance with {string} will be created")
    public void aTodoInstanceWithWillBeCreated(String title){
        JSONObject response = null;
        try {
            response = TodoInstance.send("GET", "/todos");
        } catch (IOException e) {
            error = true;
        }

        assertEquals(title,response.getJSONArray("todos").getJSONObject(2).getString("title"));
        assertEquals(false, error);
    }

    //Scenario Outline: Alternative Flow

    @Then("a todo instance with {string}, {string}, {string} will be created")
    public void aTodoInstanceWithWillBeCreated(String title, String doneStatus, String description){
        JSONObject response = null;

        try {
            response = TodoInstance.send("GET", "/todos");
        } catch (IOException e) {
            error = true;
        }

        assertEquals(title,response.getJSONArray("todos").getJSONObject(2).getString("title"));
        assertEquals(false, error);
    }

    //Scenario Outline: Error Flow
    @Then("error 404 will occur")
    public void error404WillOccur(){
        JSONObject response = null;

        try {
            response = TodoInstance.send("GET", "/todos");
        } catch (IOException e) {
            error = true;
        }

        assertEquals(true, error);
    }

    @After
    public void shutdown(){
        TodoInstance.killInstance();
        error = false;
    }

}
