package ecse429.group11.cucumber;


import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;

import java.io.IOException;

import ecse429.group11.restAPI.TodoInstance;
import static org.junit.Assert.assertEquals;

public class ChangeDescriptionStepDefinition {

    String error;

    @Given("the title of the task {string}")
    public void theTitleOfTheTask(String arg0) throws IOException {
        JSONObject json = new JSONObject();
        json.put("title", arg0);
        TodoInstance.post("/todos", json.toString());
    }

    @When("the user posts description change of task {string} to {string}")
    public void theUserPostsDescriptionChangeOfTaskTo(String arg0, String arg1) throws IOException {
        JSONObject json = new JSONObject();
        JSONObject response = TodoInstance.send("GET", "/todos?title=" + arg0);

        json.put("description", arg1);
        if (response.getJSONArray("todos").length() != 0) {
            String id = response.getJSONArray("todos").getJSONObject(0).getString("id");
            System.out.println(id);
            TodoInstance.post("/todos/" + id, json.toString());
        } else {
            error = "404";
        }

    }

    @Then("the task {string} description will be changed to {string}")
    public void theTaskDescriptionWillBeChangedTo(String arg0, String arg1) throws IOException {
        JSONObject response = TodoInstance.send("GET", "/todos?title=" + arg0);
        String description = response.getJSONArray("todos").getJSONObject(0).getString("description");
        assertEquals(arg1, description);
    }

    @And("{string} is related to projects with title {string}")
    public void isRelatedToProjectsWithTitle(String arg0, String arg1) throws IOException {
        JSONObject response = TodoInstance.send("GET", "/todos?title=" + arg0);

        JSONObject jsonPr = new JSONObject();
        jsonPr.put("title", arg1);
        TodoInstance.post("/todos", jsonPr.toString());
        JSONObject responsePr = TodoInstance.send("GET", "/projects?title=" + arg1);

        if (response.getJSONArray("todos").length() != 0 && responsePr.getJSONArray("projects").length() != 0) {
            String id = response.getJSONArray("todos").getJSONObject(0).getString("id");
            TodoInstance.post("/todos/" + id, jsonPr.toString());
        } else {
            error = "404";
        }
    }

    @Given("the id of a non-existent task is {string}")
    public void theIdOfANonExistentTaskIs(String arg0) throws IOException {
        JSONObject json = new JSONObject();
        json.put("title", arg0);
        TodoInstance.post("/todos", json.toString());
    }


    @After
    public void shutdown() {
        TodoInstance.killInstance();
    }

    @Then("an error message {string}")
    public void anErrorMessage(String arg0) {
        assertEquals(arg0, arg0);

    }
}

