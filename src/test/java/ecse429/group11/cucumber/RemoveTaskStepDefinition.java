package ecse429.group11.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class RemoveTaskStepDefinition {
    @Given("<taskid> is the id of the task")
    public void taskidIsTheIdOfTheTask() {
    }

    @And("<todolistid> is the id of the to do list")
    public void todolistidIsTheIdOfTheToDoList() {
    }

    @Then("the to do list will no longer have the task")
    public void theToDoListWillNoLongerHaveTheTask() {
    }

    @And("<categoryid> is the id of the task category")
    public void categoryidIsTheIdOfTheTaskCategory() {
    }

    @Given("{string} is the id of the non-existent task")
    public void isTheIdOfTheNonExistentTask(String arg0) {
    }

    @And("{string} is the id of the to do list")
    public void isTheIdOfTheToDoList(String arg0) {
    }

    @Then("a {string} error {int} message will be displayed")
    public void aErrorMessageWillBeDisplayed(String arg0, int arg1) {
    }
}
