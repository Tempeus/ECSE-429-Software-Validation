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

public class CategorizeTaskStepDefinition {

    public static JSONObject json = null;
    public static boolean error = false;
    public static int todoID = 0;

    @Given("the todo manager API server is already running")
    public void theTodoManagerAPIServerIsAlreadyRunning(){
        TodoInstance.runApplication();
    }

    //Scenario Outline: Normal Flow

    @Given("a todo with the title {string}, done status {string} and description {string}")
    public void aTodoWithTheTitleDoneStatusAndDescription(String title, String doneStatus, String description){
        JSONObject todo = new JSONObject();
        todo.put("title", title);
        boolean status = false;
        if (doneStatus.equals("true")){
            status = true;
        }
        todo.put("doneStatus", status);
        todo.put("description", description);

        String validID = "/todos";
        try {
            TodoInstance.post(validID,todo.toString());
        } catch (IOException e) {
            error = true;
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            error = true;
        }
    }

    @When("the user requests to give the task {string} with a priority {string}")
    public void theUserRequestsToGiveTheTaskWithAPriority(String title, String priority) throws IOException {
        JSONObject pResponse = TodoInstance.send("GET", "/categories?title=" + priority);
        String pID = pResponse.getJSONArray("categories").getJSONObject(0).getString("id");

        JSONObject tResponse = TodoInstance.send("GET", "/todos?title=" + title);
        String tID = tResponse.getJSONArray("todos").getJSONObject(0).getString("id");

        JSONObject body = new JSONObject();
        body.put("id", pID);

        TodoInstance.post("/todos/" + tID + "/categories", body.toString());
    }

    @Then("the task {string} will be assigned with the category {string}")
    public void theTaskWillBeAssignedWithTheCategory(String title, String priority) throws IOException {
        JSONObject response = null;
        try {
            response = TodoInstance.send("GET", "/todos?title=" + title);
        } catch (IOException e) {
            error = false;
        }

        JSONArray array = response.getJSONArray("todos").getJSONObject(0).getJSONArray("categories");

        JSONObject pResponse = TodoInstance.send("GET", "/categories?title=" + priority);
        String pID = pResponse.getJSONArray("categories").getJSONObject(0).getString("id");
        boolean isSet = false;
        for (int i=0; i<array.length();i++){
            if(array.getJSONObject(i).getString("id").equals(pID)){
                isSet = true;
            }
        }

        assertEquals(true, isSet);
        assertEquals(false, error);
    }

    //Scenario Outline: Alternative Flow

    @Given("a todo with the title {string}, done status {string}, description {string} and category {string}")
    public void aTodoWithTheTitleDoneStatusDescriptionAndCategory(String title, String doneStatus, String description, String priority) throws IOException {
        JSONObject todo = new JSONObject();
        todo.put("title", title);
        boolean status = false;
        if (doneStatus.equals("true")){
            status = true;
        }
        todo.put("doneStatus", status);
        todo.put("description", description);

        String validID = "/todos";
        try {
            TodoInstance.post(validID,todo.toString());
        } catch (IOException e) {
            error = true;
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            error = true;
        }

        JSONObject pResponse = TodoInstance.send("GET", "/categories?title=" + priority);
        String pID = pResponse.getJSONArray("categories").getJSONObject(0).getString("id");

        JSONObject tResponse = TodoInstance.send("GET", "/todos?title=" + title);
        String tID = tResponse.getJSONArray("todos").getJSONObject(0).getString("id");

        JSONObject body = new JSONObject();
        body.put("id", pID);

        TodoInstance.post("/todos/" + tID + "/categories", body.toString());

    }

    @When("user request to update the category of {string} from {string} to {string}")
    public void userRequestToUpdateTheCategoryOfFromTo(String title, String priority, String newPriority) throws IOException {
        JSONObject pResponse = TodoInstance.send("GET", "/categories?title=" + priority);
        String prevpID = pResponse.getJSONArray("categories").getJSONObject(0).getString("id");

        pResponse = TodoInstance.send("GET", "/categories?title=" + newPriority);
        String nextpID = pResponse.getJSONArray("categories").getJSONObject(0).getString("id");

        JSONObject tResponse = TodoInstance.send("GET", "/todos?title=" + title);
        String tID = tResponse.getJSONArray("todos").getJSONObject(0).getString("id");

        TodoInstance.send("DELETE", "/todos/" + tID + "/categories/" + prevpID);
        JSONObject body = new JSONObject();
        body.put("id", nextpID);

        TodoInstance.post("/todos/" + tID + "/categories", body.toString());
    }

    @Then("task {string} will be assigned with a new category of {string}")
    public void taskWillBeAssignedWithANewCategoryOf(String title, String newPriority) throws IOException {

        JSONObject response = null;
        try {
            response = TodoInstance.send("GET", "/todos?title=" + title);
        } catch (IOException e) {
            error = false;
        }

        JSONArray array = response.getJSONArray("todos").getJSONObject(0).getJSONArray("categories");

        JSONObject pResponse = TodoInstance.send("GET", "/categories?title=" + newPriority);
        String pID = pResponse.getJSONArray("categories").getJSONObject(0).getString("id");
        boolean isSet = false;
        for (int i=0; i<array.length();i++){
            if(array.getJSONObject(i).getString("id").equals(pID)){
                isSet = true;
            }
        }

        assertEquals(true, isSet);
        assertEquals(false, error);

    }

    //Scenario Outline: Error Flow

    @When("user request to categorize a todo with title {string} with {string}")
    public void userRequestToCategorizeATodoWithTitleWith(String fakeTitle, String priority) throws IOException {
        JSONObject response = null;
        try {
            response = TodoInstance.send("GET", "/todos?title=" + fakeTitle);
        } catch (IOException e) {
            error = true;
        }

        int length = response.getJSONArray("todos").length();

        if(length == 0){
            error = true;
        } else {
            error = false;
        }
    }

    @Then("system will output an error")
    public void systemWillOutputAnError(){
        assertEquals(true, error);
    }

    @After
    public void shutdown(){
        TodoInstance.killInstance();
        error = false;
        todoID = 0;
    }
}

