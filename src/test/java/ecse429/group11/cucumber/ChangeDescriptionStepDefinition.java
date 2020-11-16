package ecse429.group11.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class ChangeDescriptionStepDefinition {
    @Given("{string} is the new description of the task")
    public void isTheNewDescriptionOfTheTask(String arg0) {
    }

    @And("<taskid> is the given title id to be changed")
    public void taskidIsTheGivenTitleIdToBeChanged() {
    }

    @Then("the task description will be changed to {string}")
    public void theTaskDescriptionWillBeChangedTo(String arg0) {
    }

    @And("is related to projects with id <tasksofid>")
    public void isRelatedToProjectsWithIdTasksofid() {
    }

    @And("<taskid> is the given id of the non-existent task")
    public void taskidIsTheGivenIdOfTheNonExistentTask() {
    }

    @Then("a {string} error message {int} will be displayed")
    public void aErrorMessageWillBeDisplayed(String arg0, int arg1) {
    }
}
