import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateTodoStepDefinition {

    @Given("the Todo API server is running")
    public void the_Todo_API_server_is_running(){
        TodoInstance.runApplication();
    }

    @Given("{string} is the title of the class")
    public void is_the_title_of_the_class(String title){

    }

    @And("{string} is the title of the class")
    public void isTheTitleOfTheClass(String isActive){

    }

    @And("{string} is the description of the class")
    public void isTheDescriptionOfTheClass(String description){

    }

    @Given("{string} is the active status of the class")
    public void is_the_active_status_of_the_class(String activeStatus){

    }

    @And("{string} is the description of the class")
    public void is_the_description_of_the_class(String description){

    }

    //Scenario Outline: Normal Flow

    @When("the user posts a request to the server")
    public void theUserPostsARequestToTheServer(){

    }

    @Then("a todo instance with {string} will be created")
    public void aTodoInstanceWithWillBeCreated(String title){

    }

    //Scenario Outline: Alternative Flow

    @When("the user post a request to the server")
    public void theUserPostARequestToTheServer(){

    }

    @Then("a todo instance with {string}, {string}, {string} will be created")
    public void aTodoInstanceWithWillBeCreated(String title, String isActive, String description){

    }

    //Scenario Outline: Error Flow
    @Then("error 404 will occur")
    public void error404WillOccur(){

    }

    @After
    public void shutdown(){
        TodoInstance.killInstance();
    }

}
