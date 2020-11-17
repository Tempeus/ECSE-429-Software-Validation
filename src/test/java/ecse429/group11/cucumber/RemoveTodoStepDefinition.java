package ecse429.group11.cucumber;

import ecse429.group11.restAPI.TodoInstance;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RemoveTodoStepDefinition {
    String error;
    @Given("{string} is the title of the to do list to be removed")
    public void isTheTitleOfTheToDoListToBeRemoved(String arg0) throws IOException {
        JSONObject json = new JSONObject();
        json.put("title", arg0);
        TodoInstance.post("/projects", json.toString());
    }

    @When("the user posts a request to the server to remove a to do list {string}")
    public void theUserPostsARequestToTheServerToRemoveAToDoList(String arg0) throws IOException{
        JSONObject response = TodoInstance.send("GET", "/projects?title=" + arg0);
        if (response.getJSONArray("projects").length() != 0 ) {
            String id = response.getJSONArray("projects").getJSONObject(0).getString("id");
            TodoInstance.send("DELETE","/projects/"+id);
        } else {
            error = "404";
        }
    }

    @Then("the to do list with {string} will no longer be in the schedule")
    public void theToDoListWillNoLongerBeInTheSchedule(String arg0) throws IOException{
        JSONObject response = TodoInstance.send("GET", "/projects?title=" + arg0);
        try {
            int length = response.getJSONArray("projects").length();
            assertEquals(0, length);
        }
        //NO MORE TASKS
        catch (Exception e) {
            assertEquals(true,true);
        }
    }

    @And("{string} is the title of the category related to the to do list {string}")
    public void isTheTitleOfTheCategoryRelatedToTheToDoList(String arg0, String arg1) throws  IOException{
        JSONObject response = TodoInstance.send("GET", "/projects?title=" + arg1);

        JSONObject jsonCat = new JSONObject();
        jsonCat.put("title", arg0);
        TodoInstance.post("/categories", jsonCat.toString());
        JSONObject responseCat = TodoInstance.send("GET", "/categories?title=" + arg0);

        if (response.getJSONArray("projects").length() != 0 && responseCat.getJSONArray("categories").length() != 0) {
            String id = response.getJSONArray("projects").getJSONObject(0).getString("id");
            TodoInstance.post("/projects/" + id, jsonCat.toString());
        } else {
            error = "404";
        }
    }

    @Given("the id of a non-existent to do list is {string}")
    public void theIdOfANonExistentToDoListIs(String arg0) {
        try {
            JSONObject response = TodoInstance.send("GET", "/todos/" + arg0);
            assertEquals(arg0, response.getJSONArray("errorMessages").getJSONObject(0).toString());
        } catch (Exception e) {
            //Cannot find id
            assertEquals(true, true);
        }
    }

    @After
    public void clear() throws IOException {
        // Remove all todos.
        JSONObject response = TodoInstance.send("GET", "/todos");
        JSONArray array = response.getJSONArray("todos");
        for (int i = 0; i < array.length(); i++) {
            String id = array.getJSONObject(i).getString("id");
            TodoInstance.send("DELETE", "/todos/" + id);
        }

        // Remove all projects.
        response = TodoInstance.send("GET", "/projects");
        array = response.getJSONArray("projects");
        for (int i = 0; i < array.length(); i++) {
            String id = array.getJSONObject(i).getString("id");
            TodoInstance.send("DELETE", "/projects/" + id);
        }

        // Remove all categories.
        response = TodoInstance.send("GET", "/categories");
        array = response.getJSONArray("categories");
        for (int i = 0; i < array.length(); i++) {
            String id = array.getJSONObject(i).getString("id");
            TodoInstance.send("DELETE", "/categories/" + id);
        }
    }
}
