package ecse429.group11.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class RemoveTodoStepDefinition {
    @Given("<id> is the id of the to do list")
    public void idIsTheIdOfTheToDoList() {
    }

    @Then("the to do list will no longer be in the schedule")
    public void theToDoListWillNoLongerBeInTheSchedule() {
    }

    @Given("<id> is the id of a non-existent list")
    public void idIsTheIdOfANonExistentList() {
    }
}
