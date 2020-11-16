package ecse429.group11.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class RemoveTodoStepDefinition {
    @Given("<id> is the id of the to do list")
    public void idIsTheIdOfTheToDoList() {
    }

    @Then("the to do list will no longer be in the schedule")
    public void theToDoListWillNoLongerBeInTheSchedule() {
    }

    @And("<categoryid> is the id of the category related to the to do list")
    public void categoryidIsTheIdOfTheCategoryRelatedToTheToDoList() {
    }

    @And("the task will be deleted")
    public void theTaskWillBeDeleted() {
    }

    @Given("<id> is the id of a non-existent list")
    public void idIsTheIdOfANonExistentList() {
    }
}
