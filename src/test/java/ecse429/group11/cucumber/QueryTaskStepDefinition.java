package ecse429.group11.cucumber;

import ecse429.group11.restAPI.TodoInstance;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class QueryTaskStepDefinition {

    int tAmount;

    @Given("{int} todos with the title {string}, done status {string} and class {string}")
    public void todos_with_the_title_done_status_and_class(int amount, String title, String doneStatus, String className) throws IOException, InterruptedException {
        JSONObject json = new JSONObject();
        json.put("title", className);
        TodoInstance.post("/projects", json.toString());

        json = new JSONObject();
        json.put("title", title);
        boolean status = false;
        if (doneStatus.equals("true")){
            status = true;
        }
        json.put("doneStatus", status);

        for(int i=0; i<amount; i++){
            TodoInstance.post("/todos", json.toString());
            Thread.sleep(500);
        }

        JSONObject tResponse = TodoInstance.send("GET", "/todos?title=" + title);
        Thread.sleep(500);
        JSONObject pResponse = TodoInstance.send("GET", "/projects?title=" + className);

        String pID = pResponse.getJSONArray("projects").getJSONObject(0).getString("id");
        System.out.println(pID);

        JSONObject body = new JSONObject();
        body.put("id", pID);
        for (int i=0; i<amount; i++){
            String tID = tResponse.getJSONArray("todos").getJSONObject(i).getString("id");
            TodoInstance.post("/todos/" + tID + "/tasksof", body.toString());
            Thread.sleep(500);
        }
    }
    @When("the user queries all tasks with done status {string} and class {string}")
    public void the_user_queries_all_tasks_with_done_status_and_class(String doneStatus, String className) throws IOException {
        JSONObject pResponse = TodoInstance.send("GET", "/projects?title=" + className);
        JSONArray array = pResponse.getJSONArray("projects");
        if (array.length() != 0) {
            String pID = array.getJSONObject(0).getString("id");

            JSONObject response = TodoInstance.send("GET", "/projects/" + pID);

            tAmount = response.getJSONArray("projects").getJSONObject(0).getJSONArray("tasks").length();
        } else {
            tAmount = 0;
        }
    }

    @Then("{int} tasks with done status {string} and class {string} will be returned")
    public void tasks_with_done_status_and_class_will_be_returned(int amount, String doneStatus, String className){
        assertEquals(amount, tAmount);
    }

    @Given("no todos with class {string}")
    public void no_todos_with_class(String className) throws IOException {
        // Remove all todos.
        JSONObject response = TodoInstance.send("GET", "/todos");
        JSONArray array = response.getJSONArray("todos");
        for (int i=0; i<array.length(); i++){
            String id = array.getJSONObject(i).getString("id");
            TodoInstance.send("DELETE", "/todos/" + id);
        }
    }
}
