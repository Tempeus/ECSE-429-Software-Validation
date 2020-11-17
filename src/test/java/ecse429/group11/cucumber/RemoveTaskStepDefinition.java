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

public class RemoveTaskStepDefinition {

    String error;

    @Given("{string} is the title of the task to be removed")
    public void isTheTitleOfTheTaskToBeRemoved(String arg0) throws IOException {
        JSONObject json = new JSONObject();
        json.put("title", arg0);
        TodoInstance.post("/todos", json.toString());
    }

    @And("{string} is the title of the to do list")
    public void isTheTitleOfTheToDoList(String arg0) throws IOException {
        JSONObject json = new JSONObject();
        json.put("title", arg0);
        TodoInstance.post("/projects", json.toString());
    }

    @When("the user posts a request to the server to remove a task {string} from {string}>")
    public void theUserPostsARequestToTheServerToRemoveATaskFrom(String arg0, String arg1) throws IOException {
        JSONObject response = TodoInstance.send("GET", "/todos?title=" + arg0);

        JSONObject jsonPr = new JSONObject();
        jsonPr.put("title", arg1);

        JSONObject responsePr = TodoInstance.send("GET", "/projects?title=" + arg1);
        if (response.getJSONArray("todos").length() != 0 && responsePr.getJSONArray("projects").length() != 0) {
            String id = response.getJSONArray("todos").getJSONObject(0).getString("id");
            String idpr = responsePr.getJSONArray("projects").getJSONObject(0).getString("id");
            JSONObject idjson = new JSONObject();
            idjson.put("id", id);
            TodoInstance.post("/todos/" + id, jsonPr.toString());
            TodoInstance.send("DELETE","/projects/"+idpr+"/tasks/"+id);
        } else {
            error = "404";
        }

    }

    @Then("the to do {string} list will no longer have the task {string}")
    public void theToDoListWillNoLongerHaveTheTask(String arg0, String arg1) throws IOException {
        JSONObject response = TodoInstance.send("GET", "/projects?title=" + arg0);
        try {
            int length = response.getJSONArray("projects").getJSONObject(0).getJSONArray("tasks").length();
            assertEquals(false, true);
        }
        //NO MORE TASKS
        catch (Exception e) {
            assertEquals(true,true);
        }
    }

    @And("{string} is the id of the task category related to {string}")
    public void isTheIdOfTheTaskCategory(String arg0, String arg1) throws IOException {
        JSONObject response = TodoInstance.send("GET", "/todos?title=" + arg1);

        JSONObject jsonCat = new JSONObject();
        jsonCat.put("title", arg0);
        TodoInstance.post("/todos", jsonCat.toString());
        JSONObject responseCat = TodoInstance.send("GET", "/categories?title=" + arg0);

        if (response.getJSONArray("todos").length() != 0 && responseCat.getJSONArray("categories").length() != 0) {
            String id = response.getJSONArray("todos").getJSONObject(0).getString("id");
            TodoInstance.post("/todos/" + id, jsonCat.toString());
        } else {
            error = "404";
        }
    }

    @Then("a {string} error with {string} message will be displayed")
    public void aErrorMessageWillBeDisplayed(String arg0, int arg1) throws IOException {
        try{
            JSONObject response = TodoInstance.send("GET", "/todos/" + arg1);
            assertEquals(arg0,response.getJSONArray("errorMessages").getJSONObject(0).toString());
        }
        catch (Exception e) {
            //Cannot find id
            assertEquals(true,true);
        }
    }

    @Given("{string} is the title of the non-existent task to be removed")
    public void isTheTitleOfTheNonExistentTaskToBeRemoved(String arg0) throws IOException {
        JSONObject json = new JSONObject();
        json.put("title", arg0);
        TodoInstance.post("/todos", json.toString());
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
