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

    @Given("{string} is the active state of the class")
    public void is_the_done_status_of_the_class(String active){
        boolean status = false;
        if (active.equals("true")){
            status = true;
        }
        json.put("active", status);
    }

    @Given("{string} is the completion state of the class")
    public void is_the_completion_state_of_the_class(String completed){
        boolean status = false;
        if (completed.equals("true")){
            status = true;
        }
        json.put("completed", status);
    }

    //Scenario Outline: Normal Flow

    @When("the user creates a new to do list for a class")
    public void the_user_creates_a_new_class(){
        System.out.println(json.toString());
        if (!(json.has("title"))){
            error = true;
        }
        try {
            TodoInstance.post("/projects", json.toString());
        } catch (IOException e) {
            error = true;
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("a todo list instance with {string} will be created")
    public void aTodoInstanceWithWillBeCreated(String title){
        JSONObject response = null;
        try {
            response = TodoInstance.send("GET", "/projects?title=" + title);
        } catch (IOException e) {
            error = true;
        }

        assertEquals(title, response.getJSONArray("projects").getJSONObject(0).getString("title"));
    }

    //Scenario Outline: Alternative Flow

    @Then("a todo instance with {string}, {string}, {string}, {string} will be created")
    public void aTodoInstanceWithWillBeCreated(String title, String active, String completed, String description){
        JSONObject response = null;

        try {
            response = TodoInstance.send("GET", "/projects?title=" + title);
        } catch (IOException e) {
            error = true;
        }

        JSONObject todoList = response.getJSONArray("projects").getJSONObject(0);

        assertEquals(title, todoList.getString("title"));
        assertEquals(active, todoList.getString("active"));
        assertEquals(completed, todoList.getString("completed"));
        assertEquals(description, todoList.getString("description"));
    }

    //Scenario Outline: Error Flow
    @Then("error 404 will occur")
    public void error404WillOccur(){
        // assuming we aren't supposed to be able to create a project without a title.
        assertEquals(true, error);
    }

    @After
    public void shutdown(){
        TodoInstance.killInstance();
        error = false;
    }

}
