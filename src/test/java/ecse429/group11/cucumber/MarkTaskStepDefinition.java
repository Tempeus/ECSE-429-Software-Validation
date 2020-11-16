package ecse429.group11.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import ecse429.group11.restAPI.TodoInstance;

import static org.junit.Assert.assertEquals;

public class MarkTaskStepDefinition {

    String error;

    @Given("a todo with the title {string} and done status {string}")
    public void a_todo_with_the_title_and_done_status(String title, String prevDoneStatus) throws IOException {
        JSONObject json = new JSONObject();
        json.put("title", title);
        boolean doneStatus = false;
        if (prevDoneStatus.equals("true")){
            doneStatus = true;
        }
        json.put("doneStatus", doneStatus);
        TodoInstance.post("/todos", json.toString());
    }

    @When("the user requests to mark the task {string} with a done status {string}")
    public void the_user_requests_to_mark_the_task_with_a_done_status(String title, String nextDoneStatus) throws IOException {
        JSONObject json = new JSONObject();
        boolean doneStatus = false;
        if (nextDoneStatus.equals("true")){
            doneStatus = true;
        }
        json.put("doneStatus", doneStatus);
        JSONObject response = TodoInstance.send("GET", "/todos?title=" + title);

        if (response.getJSONArray("todos").length() != 0){
            String id = response.getJSONArray("todos").getJSONObject(0).getString("id");
            TodoInstance.post("/todos/" + id, json.toString());
        } else {
            error = "404";
        }
    }

    @Then("the task {string} will be marked with the done status {string}")
    public void the_task_will_be_marked_with_the_done_status(String title, String nextDoneStatus) throws IOException {
        JSONObject response = TodoInstance.send("GET", "/todos?title=" + title);
        String doneStatus = response.getJSONArray("todos").getJSONObject(0).getString("doneStatus");
        assertEquals(nextDoneStatus, doneStatus);
    }

    @Given("no todo with id {string} is registered in the API server")
    public void no_todo_with_id_is_registered_in_the_API_server(String id) throws IOException {
        JSONObject response = TodoInstance.send("DELETE", "/todos/" + id);
    }

    @Then("system will output an error with error code {string}")
    public void system_will_output_an_error_with_error_code(String errorCode){
        assertEquals(error, errorCode);
    }
}
