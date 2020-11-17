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
        JSONObject cResponse = TodoInstance.send("GET", "/categories?title=" + old_priority);
        String categoryID = cResponse.getJSONArray("categories").getJSONObject(0).getString("id");

        JSONObject categoryJSON = new JSONObject();
        categoryJSON.put("id", categoryID);

        try {
            TodoInstance.post("/todos/"+todoID+"/categories",categoryJSON.toString());
        } catch (IOException e) {
            error = true;
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            error = true;
        }
    }

    @When("the student changes the task {string} from {string} priority to {string} priority")
    public void theStudentChangesTheTaskToPriority(String title, String old_priority, String new_priority) throws Throwable {
        //Get to do ID from its title
        JSONObject tResponse = TodoInstance.send("GET", "/todos?title=" + title);
        String todoID = tResponse.getJSONArray("todos").getJSONObject(0).getString("id");

        //Get old category ID from its title
        JSONObject cOldResponse = TodoInstance.send("GET", "/categories?title=" + old_priority);
        String oldCategoryID = cOldResponse.getJSONArray("categories").getJSONObject(0).getString("id");

        //Get new category ID from its title
        JSONObject cNewResponse = TodoInstance.send("GET", "/categories?title=" + new_priority);
        String newCategoryID = cNewResponse.getJSONArray("categories").getJSONObject(0).getString("id");

        //Delete Previous Connection
        TodoInstance.send("DELETE", "/todos/" + todoID + "/categories/" + oldCategoryID);

        //Recreate
        JSONObject body = new JSONObject();
        body.put("id", newCategoryID);
        TodoInstance.post("/todos/" + todoID + "/categories", body.toString());
    }

    @Then("the priority of the task {string} should be {string}")
    public void thePriorityOfTheTaskShouldBe(String title, String new_priority) throws Throwable {
        //Get category ID from its title
        JSONObject cResponse = TodoInstance.send("GET", "/categories?title=" + new_priority);
        String categoryID = cResponse.getJSONArray("categories").getJSONObject(0).getString("id");

        //Get to do ID from its title
        JSONObject tResponse = TodoInstance.send("GET", "/todos?title=" + title);
        String todoID = tResponse.getJSONArray("todos").getJSONObject(0).getString("id");

        JSONArray array = tResponse.getJSONArray("todos").getJSONObject(0).getJSONArray("categories");
        boolean isSet = false;
        for (int i=0; i<array.length();i++){
            if(array.getJSONObject(i).getString("id").equals(categoryID)){
                isSet = true;
            }
        }
        assertTrue(isSet);
    }

    @When("the student changes the task with the wrong title {string} from {string} priority to {string} priority")
    public void theStudentChangesTheTaskWithTheWrongTitleToPriority(String wrongTitle, String old_priority, String new_priority) throws Throwable {
        JSONObject response = null;
        try {
            response = TodoInstance.send("GET", "/todos?title=" + wrongTitle);
        } catch (IOException e) {
            error = true;
        }

        int length = response.getJSONArray("todos").length();

        if (length == 0){
            error = true;
        } else {
            error = false;
        }
    }

    @Then("the system shall inform the user that the task is non-existent")
    public void theSystemShallInformTheUserThatTheTaskIsNonExistent() {
        assertTrue(error);
    }
}
