package ecse429.group11.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class RemoveTodoStepDefinition {
    @Then("the to do list will no longer be in the schedule")
    public void theToDoListWillNoLongerBeInTheSchedule() {
    }

    @And("{string} is the id of the category related to the to do list")
    public void isTheIdOfTheCategoryRelatedToTheToDoList(String arg0) {
    }

    @And("the task will be deleted")
    public void theTaskWillBeDeleted() {
    }

    @Given("{string} is the id of a non-existent list")
    public void isTheIdOfANonExistentList(String arg0) {
    }
}
