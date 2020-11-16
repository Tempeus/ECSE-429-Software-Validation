package ecse429.group11.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;

import java.io.IOException;

public class RemoveTaskStepDefinition {

    public static JSONObject json = null;
    public static boolean error = false;

    @Given("{string} is the id of the task")
    public void isTheIdOfTheTask(String arg0) {
        json.put("taskid", arg0);
    }

    @And("{string} is the id of the to do list")
    public void isTheIdOfTheToDoList(String arg0) {
        json.put("todolistid", arg0);
    }

    @Then("the to do list will no longer have the task")
    public void theToDoListWillNoLongerHaveTheTask() {
        String validID = "/todos";
        try {
            ecse429.group11.restAPI.TodoInstance.post(validID,json.toString());
        } catch (IOException e) {
            error = true;
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @And("{string} is the id of the task category")
    public void isTheIdOfTheTaskCategory(String arg0) {
    }

    @Given("{string} is the id of the non-existent task")
    public void isTheIdOfTheNonExistentTask(String arg0) {
    }

    @Then("a {string} error {int} message will be displayed")
    public void aErrorMessageWillBeDisplayed(String arg0, int arg1) {
    }
}
