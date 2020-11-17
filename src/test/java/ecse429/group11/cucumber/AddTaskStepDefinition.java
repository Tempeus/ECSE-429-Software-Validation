package ecse429.group11.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import ecse429.group11.restAPI.TodoInstance;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddTaskStepDefinition {
    public static JSONObject json = null;
    public static boolean error = false;
    public static int todoID = 0;

    @Given("there exists a project with title {string}")
    public void thereExistsATodoListInTheSystemWithTitle(String projectTitle) throws Throwable {
        JSONObject projectJson = new JSONObject();
        projectJson.put("title",projectTitle);

        String validID = "/projects";

        try {
            TodoInstance.post(validID,projectJson.toString());
        } catch (IOException e) {
            error = true;
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            error = true;
        }
    }

    @When("I add a new task with the title {string} to the todo list {string}")
    public void iAddANewTaskWithTheTitleToTheTodoList(String taskTitle, String projectTitle) throws Throwable {
        //Create a to do with taskTitle title
        String validID = "/todos";
        JSONObject todoJson = new JSONObject();
        todoJson.put("title", taskTitle);
        try {
            TodoInstance.post(validID,todoJson.toString());
        } catch (IOException e) {
            error = true;
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Get project ID from its title
        JSONObject pResponse = TodoInstance.send("GET", "/projects?title=" + projectTitle);
        String projectID = pResponse.getJSONArray("projects").getJSONObject(0).getString("id");

        //Get to do ID from its title
        JSONObject tResponse = TodoInstance.send("GET", "/todos?title=" + taskTitle);
        String todoID = tResponse.getJSONArray("todos").getJSONObject(0).getString("id");

        JSONObject relationJson = new JSONObject();
        relationJson.put("id", todoID);
        try {
            TodoInstance.post("/projects/"+projectID+"/tasks",relationJson.toString());
        } catch (IOException e) {
            error = true;
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("there will be a new task with title {string} in the todo list {string}")
    public void thereWillBeANewTaskWithTitleInTheTodoList(String taskTitle, String projectTitle) throws Throwable {
        //Get project ID from its title
        JSONObject pResponse = TodoInstance.send("GET", "/projects?title=" + projectTitle);
        String projectID = pResponse.getJSONArray("projects").getJSONObject(0).getString("id");

        //Get to do ID from its title
        JSONObject tResponse = TodoInstance.send("GET", "/todos?title=" + taskTitle);
        String todoID = tResponse.getJSONArray("todos").getJSONObject(0).getString("id");

        JSONObject response = TodoInstance.send("GET","/projects/"+projectID+"/tasks");
        assertEquals(todoID,response.getJSONArray("todos").getJSONObject(0).getString("id"));
    }

    @When("I add a new task with the title {string} to the wrong todo list {string}")
    public void iAddANewTaskWithTheTitleToTheWrongTodoList(String taskTitle, String wrongTitle) {
        //Create a to do with taskTitle title
        String validID = "/todos";
        JSONObject todoJson = new JSONObject();
        todoJson.put("title", taskTitle);
        try {
            TodoInstance.post(validID,todoJson.toString());
        } catch (IOException e) {
            error = true;
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject pResponse = new JSONObject();
        JSONObject tResponse = new JSONObject();
        String projectID = null;
        String todoID = null;


        //Get project ID from its title
        try{
            pResponse = TodoInstance.send("GET", "/projects?title=" + wrongTitle);
            projectID = pResponse.getJSONArray("projects").getJSONObject(0).getString("id");
        }  catch (IOException e) {
            error = true;
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Get to do ID from its title
        try{
            tResponse = TodoInstance.send("GET", "/todos?title=" + taskTitle);
            todoID = tResponse.getJSONArray("todos").getJSONObject(0).getString("id");
        } catch (IOException e) {
            error = true;
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject relationJson = null;
        relationJson.put("id", todoID);
        try {
            TodoInstance.post("/projects/"+projectID+"/tasks",relationJson.toString());
        } catch (IOException e) {
            error = true;
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("the system will inform the user that the todo list {string} is non-existent")
    public void theSystemWillInformTheUserThatTheTodoListIsNonExistent(String projectTitle) throws Throwable {
        assertTrue(error);
    }

}
