package ecse429.group11;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class TestProjects {

    @Before
    public void startInstance(){
        TodoInstance.runApplication();
    }

    //GET Projects
    @Test
    public void testGetProjectStatus() throws IOException {
        String project_url = "/projects";
        assertEquals(TodoInstance.SC_SUCCESS,TodoInstance.getStatusCode(project_url));
    }

    @Test
    public void testGetProjectSize() throws IOException {
        String project_url = "/projects";
        JSONObject response = TodoInstance.send("GET", project_url);
        assertEquals(1,response.getJSONArray("projects").length());
    }

    @Test
    public void testGetProjectTitle() throws IOException {
        String project_url = "/projects";
        JSONObject response = TodoInstance.send("GET", project_url);
        String result = response.getJSONArray("projects").getJSONObject(0).getString("title");
        assertEquals("Office Work", result);
    }

    @Test
    public void testGetProjectCompletionStatus() throws IOException {
        String project_url = "/projects";
        JSONObject response = TodoInstance.send("GET", project_url);
        String result = response.getJSONArray("projects").getJSONObject(0).getString("completed");
        assertEquals("false", result);
    }

    @Test
    public void testGetInvalidProjectTitle() throws IOException {
        //todo: Can you get using title?
    }

    //GET projects/id
    @Test
    public void testGetSpecificProjectUsingID() throws IOException {
        JSONObject response = TodoInstance.send("GET", "/projects/1");
        String expected = "1";
        String result = response.getJSONArray("projects").getJSONObject(0).getString("id");
        assertEquals(result, expected);
    }

    @Test
    public void testGetInvalidID() throws IOException {
        String invalid_request = "/projects/3";
        assertEquals(TodoInstance.getStatusCode(invalid_request), TodoInstance.SC_NOT_FOUND);
    }

    @Test
    public void testGetTaskOfProjects() throws IOException {
        String valid_request = "/projects/1/tasks";
        assertEquals(TodoInstance.getStatusCode(valid_request),TodoInstance.SC_SUCCESS);
        JSONObject response = TodoInstance.send("GET", valid_request);
        assertEquals(2,response.getJSONArray("todos").length());
    }

    @Test
    public void testGetInvalidTaskOfProjects() throws IOException {
        String invalid_taskof = "/projects/1/tasks/3";
        int result = TodoInstance.getStatusCode(invalid_taskof);
        assertEquals(TodoInstance.SC_NOT_FOUND, result);
    }

    @Test
    public void testGetCategoriesOfProjects() throws IOException {
        String valid_request = "/projects/1/categories";
        JSONObject response = TodoInstance.send("GET",valid_request);
        assertEquals(0,response.getJSONArray("categories").length());
    }

    @Test
    public void testGetInvalidCategoriesOfProjects() throws IOException {
        String invalid_categories = "/todos/1/categories/3";
        int result = TodoInstance.getStatusCode(invalid_categories);
        assertEquals(TodoInstance.SC_NOT_FOUND, result);
    }
    //POST Projects

    @Test
    public void testCreateProject(){

    }

    @Test
    public void testCreateProjectWithTitle(){

    }

    @Test
    public void testCreateProjectWithNoTitle(){

    }

    @Test
    public void testCreateProjectWithNoInfo(){

    }

    //DELETE Projects
    @Test
    public void testDeleteAllProjects() throws IOException {
        String option = "/projects";
        assertEquals(TodoInstance.SC_UNSUPPORTED,TodoInstance.getStatusCode("DELETE", option));
    }

    //DELETE Projects/id
    @Test
    public void testDeleteValidProjects() throws IOException {
        String valid_id = "/projects/1";
        assertEquals(TodoInstance.getStatusCode(valid_id), TodoInstance.SC_SUCCESS);
        TodoInstance.send("DELETE",valid_id);
        assertEquals(TodoInstance.getStatusCode(valid_id), TodoInstance.SC_NOT_FOUND);
    }

    @Test
    public void testDeleteInvalidProjects() throws IOException {
        String invalid_id = "/projects/3";

        JSONObject result = TodoInstance.send("DELETE",invalid_id);
        assertEquals(null, result);
    }

    //Delete relationship between projects and todos
    @Test
    public void testDeleteTaskOfProjects() throws IOException {
        String valid_task = "/projects/1/tasks/1";
        TodoInstance.send("DELETE",valid_task);
        JSONObject response = TodoInstance.send("GET","/projects/1/tasks");
        //todo: ASSERT NEEDED HERE
    }

    @Test
    public void testDeleteInvalidTaskOfProjects() throws IOException {
        String invalid_taskof = "/todos/1/tasksof/3";
        int result = TodoInstance.getStatusCode("DELETE",invalid_taskof);
        assertEquals(TodoInstance.SC_NOT_FOUND, result);
    }

    //HEAD Projects
    @Test
    public void testHeadProjects() throws IOException {
        String option = "/projects";
        String head = TodoInstance.getHeadContentType(option);
        assertEquals("application/json", head);
    }

    @Test
    public void testHeadProjectsID() throws IOException {
        String option = "/projects/1";
        String head = TodoInstance.getHeadContentType(option);
        assertEquals("application/json", head);
    }

}
