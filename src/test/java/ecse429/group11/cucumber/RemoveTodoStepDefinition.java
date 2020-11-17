package ecse429.group11.cucumber;

import ecse429.group11.restAPI.TodoInstance;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class RemoveTodoStepDefinition {

    public static JSONObject json = null;
    public static boolean error = false;

    @Given("{string} is the id of the to do list to remove a to do list")
    public void isTheIdOfTheToDoListForToDoList(String arg0) {
        json.put("todolistid", arg0);
    }

    @When("the user posts a request to the server to remove a to do list")
    public void theUserPostsADeleteTodoRequestToTheServer(){
        String validID = "/projects";
        try {
            TodoInstance.delete(validID,json.get("tasktodoid").toString());
        } catch (IOException e) {
            error = true;
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("the to do list will no longer be in the schedule")
    public void theToDoListWillNoLongerBeInTheSchedule() {
        JSONObject response = null;
        try {
            response = TodoInstance.send("GET", "/projects");
        } catch (IOException e) {
            error = true;
        }
        assertEquals(null, response.getJSONArray("projects").getJSONObject(0).get("id").toString());
        assertEquals(false, error);
    }

    @And("{string} is the id of the category related to the to do list")
    public void isTheIdOfTheCategoryRelatedToTheToDoList(String arg0) {
        json.put("categorytaskid", arg0);
    }

    @And("the task will be deleted")
    public void theTaskWillBeDeleted() {
        theToDoListWillNoLongerBeInTheSchedule();
    }

    @Given("{string} is the id of a non-existent list")
    public void isTheIdOfANonExistentList(String arg0) {
        json.put("nonexistentlistid", arg0);
    }
}
