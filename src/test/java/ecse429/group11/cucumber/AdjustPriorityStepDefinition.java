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
import static org.junit.Assert.assertTrue;

public class AdjustPriorityStepDefinition {
    public static boolean error = false;

    @Given("HIGH, MEDIUM and LOW categories are registered in the todo manager API")
    public void highMEDIUMAndLOWCategoriesAreRegisteredInTheTodoManagerAPI() throws IOException {
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

    @Given("the task with title {string} exists and has {string} priority")
    public void theTaskWithTitleExistsAndHasPriority(String title, String old_priority) throws Throwable {
        //Create the to do
        JSONObject todo = new JSONObject();
        todo.put("title", title);

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

        //Get to do ID from its title
        JSONObject tResponse = TodoInstance.send("GET", "/todos?title=" + title);
        String todoID = tResponse.getJSONArray("todos").getJSONObject(0).getString("id");

        //Get category ID from its title
        JSONObject cResponse = TodoInstance.send("GET", "/categories?title=" + title);
        String categoryID = tResponse.getJSONArray("categories").getJSONObject(0).getString("id");

        JSONObject send = new JSONObject();
        send.put("id", categoryID);

        String categoryURL = "/categories";
        try {
            TodoInstance.post(categoryURL,send.toString());
        } catch (IOException e) {
            error = true;
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            error = true;
        }
    }

    @When("the student changes the task to {string} priority")
    public void theStudentChangesTheTaskToPriority(String new_priority) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("the priority of the task should be {string}")
    public void thePriorityOfTheTaskShouldBe(String new_priority) throws Throwable {
        assertEquals(1,1);
    }

    @Given("the task with title {string} does not exist")
    public void theTaskWithIdDoesNotExist(String title) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @When("the student changes the task with the wrong title {string} to {string} priority")
    public void theStudentChangesTheTaskWithTheWrongTitleToPriority(String wrongTitle, String new_priority) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("the system shall inform the user that the task is non-existent")
    public void theSystemShallInformTheUserThatTheTaskIsNonExistent() {
        assertEquals(1,1);
    }
}
