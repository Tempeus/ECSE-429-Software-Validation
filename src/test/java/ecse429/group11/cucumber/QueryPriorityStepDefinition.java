package ecse429.group11.cucumber;

import ecse429.group11.restAPI.TodoInstance;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;

import java.io.IOException;

public class QueryPriorityStepDefinition {

    String error;

    @Given("HIGH, MEDIUM and LOW categories exist")
    public void HIGH_MEDIUM_and_LOW_categories_exist() throws IOException {
        JSONObject json1 = new JSONObject();
        json1.put("title", "HIGH");
        JSONObject json2 = new JSONObject();
        json2.put("title", "MEDIUM");
        JSONObject json3 = new JSONObject();
        json3.put("title", "LOW");
        TodoInstance.post("/categories", json1.toString());
        TodoInstance.post("/categories", json2.toString());
        TodoInstance.post("/categories", json3.toString());
    }

    @Given("{int} todos with the title {string}, done status {string} and priority {string}")
    public void todos_with_the_title_done_status_and_priority(int amount, String title, String doneStatus, String priority) throws IOException {
        JSONObject json = new JSONObject();
        json.put("title", title);
        boolean status = false;
        if (doneStatus.equals("true")){
            status = true;
        }
        json.put("doneStatus", doneStatus);

        for (int i=0; i<amount; i++){
            TodoInstance.post("/todos", json.toString());
        }

        JSONObject tResponse = TodoInstance.send("GET", "/todos?title=" + title);
        JSONObject pResponse = TodoInstance.send("GET", "/categories?title=" + priority);

        String pID = pResponse.getJSONArray("categories").getJSONObject(0).getString("id");
        JSONObject body = new JSONObject();
        body.put("id", pID);
        for (int i=0; i<amount; i++){
            String tID = tResponse.getJSONArray("todos").getJSONObject(i).getString("id");
            TodoInstance.post("/todos/" + tID + "/categories", body.toString());
        }
    }

    @When("the user queries all tasks with done status {string} and priority {string}")
    public void the_user_queries_all_tasks_with_done_status_and_priority(String doneStatus, String priority){

    }

    @Then("{int} tasks with done status {string} and priority {string} will be returned")
    public void tasks_with_done_status_and_priority_will_be_returned(int amount, String doneStatus, String priority){

    }

    @Given("no todos with priority {string}")
    public void no_todos_with_priority(String priority){

    }
}
