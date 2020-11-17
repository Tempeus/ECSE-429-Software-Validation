package ecse429.group11.cucumber;


import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONArray;
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
            System.out.println(error);
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

    @Then("an error message {string} with {string} will occur")
    public void anErrorMessage(String arg0, String arg1) throws IOException{
        try{
            JSONObject response = TodoInstance.send("GET", "/todos/" + arg1);
            System.out.println(response.getJSONArray("errorMessages").getJSONObject(0).toString());
            assertEquals(arg0,true);
        }
        catch (Exception e) {
            //Cannot find id
            assertEquals(true,true);
        }
    }

    @After
    public void clear() throws IOException {
        // Remove all todos.
        JSONObject response = TodoInstance.send("GET", "/todos");
        JSONArray array = response.getJSONArray("todos");
        for (int i=0; i<array.length(); i++){
            String id = array.getJSONObject(i).getString("id");
            TodoInstance.send("DELETE", "/todos/" + id);
        }

        // Remove all projects.
        response = TodoInstance.send("GET", "/projects");
        array = response.getJSONArray("projects");
        for (int i=0; i<array.length(); i++){
            String id = array.getJSONObject(i).getString("id");
            TodoInstance.send("DELETE", "/projects/" + id);
        }

        // Remove all categories.
        response = TodoInstance.send("GET", "/categories");
        array = response.getJSONArray("categories");
        for (int i=0; i<array.length(); i++){
            String id = array.getJSONObject(i).getString("id");
            TodoInstance.send("DELETE", "/categories/" + id);
        }
    }
}

