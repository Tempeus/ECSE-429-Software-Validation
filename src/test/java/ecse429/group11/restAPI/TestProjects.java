package ecse429.group11.restAPI;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

@FixMethodOrder(MethodSorters.JVM)
public class TestProjects {

    @Before
    public void startInstance(){
        TodoInstance.runApplication();
    }

    @After
    public void killInstance(){
        TodoInstance.killInstance();
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
    public void testCreateValidProjects() throws IOException, InterruptedException {
        String validID = "/projects";
        String title = "TitleTest";
        String description = "DescriptionTest";

        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("description", description);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/projects");
        System.out.println(response);
        assertEquals(2,response.getJSONArray("projects").length());
    }

    @Test
    public void testCreateWithOnlyTitle() throws IOException, InterruptedException {
        String validID = "/projects";
        String title = "TitleTest";

        JSONObject json = new JSONObject();
        json.put("title", title);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/projects");
        System.out.println(response);
        assertEquals(2,response.getJSONArray("projects").length());
    }

    @Test
    public void testCreateWithExistingTitle() throws IOException, InterruptedException {
        String validID = "/projects";
        String title = "Office Work";

        JSONObject json = new JSONObject();
        json.put("title", title);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/projects");
        assertEquals(2,response.getJSONArray("projects").length());
    }

    @Test
    public void testCreateWithoutTitle() throws IOException, InterruptedException {
        JSONObject json = new JSONObject();
        json.put("description", "DescriptionTest");
        TodoInstance.post("/projects", json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/projects");
        assertEquals(2,response.getJSONArray("projects").length());
    }

    @Test
    public void testCreateTasksOfProjects() throws IOException, InterruptedException {
        JSONObject json = new JSONObject();
        json.put("id", "1");
        TodoInstance.post("/projects/1/tasks", json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/projects/1/tasks");
        assertEquals(2,response.getJSONArray("todos").length());
    }

    @Test
    public void testCreateCategoriesOfProjects() throws IOException, InterruptedException {
        JSONObject json = new JSONObject();
        json.put("id", "1");
        TodoInstance.post("/projects/1/categories", json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/projects/1/categories");
        assertEquals(1,response.getJSONArray("categories").length());
    }

    //POST projects/id
    @Test
    public void testUpdateTitle() throws IOException, InterruptedException {
        String validID = "/projects/1";
        String title = "NEWTITLE";

        JSONObject json = new JSONObject();
        json.put("title", title);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/projects/1");
        assertEquals(title,response.getJSONArray("projects").getJSONObject(0).get("title"));

    }

    @Test
    public void testUpdateDescription() throws IOException, InterruptedException {
        String validID = "/projects/1";
        String description = "DESCRIPTION";

        JSONObject json = new JSONObject();
        json.put("description", description);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/projects/1");
        assertEquals("DESCRIPTION",response.getJSONArray("projects").getJSONObject(0).get("description"));
    }

    @Test
    public void testUpdateCompletion() throws IOException, InterruptedException {
        String validID = "/projects/1";
        boolean completed = true;

        JSONObject json = new JSONObject();
        json.put("completed", completed);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/projects/1");
        assertEquals("true",response.getJSONArray("projects").getJSONObject(0).get("completed"));
    }

    @Test
    public void testUpdateActive() throws IOException, InterruptedException {
        String validID = "/projects/1";
        boolean active = true;

        JSONObject json = new JSONObject();
        json.put("active", active);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/projects/1");
        assertEquals("true",response.getJSONArray("projects").getJSONObject(0).get("active"));
    }

    //PUT projects/id
    @Test
    public void testOverrideTitle() throws IOException, InterruptedException {
        String validID = "/projects/1";
        String title = "NEWTITLE";

        JSONObject json = new JSONObject();
        json.put("title", title);
        TodoInstance.put(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/projects/1");
        assertEquals(title,response.getJSONArray("projects").getJSONObject(0).get("title"));
        boolean NotExist = false;
        try{
            response.getJSONArray("projects").getJSONObject(0).get("tasks");
        } catch(JSONException e) {
            NotExist = true;
        }

        assertEquals(true,NotExist);
    }

    @Test
    public void testOverrideDescription() throws IOException, InterruptedException {
        String validID = "/projects/1";
        String title = "NEWTITLE";
        String description = "DESCRIPTION";

        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("description", description);
        TodoInstance.put(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/projects/1");
        assertEquals(title,response.getJSONArray("projects").getJSONObject(0).get("title"));
        assertEquals("DESCRIPTION",response.getJSONArray("projects").getJSONObject(0).get("description"));
        boolean NotExist = false;
        try{
            response.getJSONArray("projects").getJSONObject(0).get("tasks");
        } catch(JSONException e) {
            NotExist = true;
        }

        assertEquals(true,NotExist);
    }

    @Test
    public void testOverrideCompleted() throws IOException, InterruptedException {
        String validID = "/projects/1";
        String title = "NEWTITLE";
        boolean completed = true;

        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("completed", completed);
        TodoInstance.put(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/projects/1");
        assertEquals(title,response.getJSONArray("projects").getJSONObject(0).get("title"));
        assertEquals("true",response.getJSONArray("projects").getJSONObject(0).get("completed"));
        boolean NotExist = false;
        try{
            response.getJSONArray("projects").getJSONObject(0).get("tasks");
        } catch(JSONException e) {
            NotExist = true;
        }

        assertEquals(true,NotExist);
    }

    @Test
    public void testOverrideActive() throws IOException, InterruptedException {
        String validID = "/projects/1";
        String title = "NEWTITLE";
        boolean active = true;

        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("active", active);
        TodoInstance.put(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/projects/1");
        assertEquals(title,response.getJSONArray("projects").getJSONObject(0).get("title"));
        assertEquals("true",response.getJSONArray("projects").getJSONObject(0).get("active"));
        boolean NotExist = false;
        try{
            response.getJSONArray("projects").getJSONObject(0).get("tasks");
        } catch(JSONException e) {
            NotExist = true;
        }

        assertEquals(true,NotExist);
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
        response.getJSONArray("todos").getJSONObject(0).getJSONArray("tasksof");
        assertEquals(1, response.length());
    }

    @Test
    public void testDeleteInvalidTaskOfProjects() throws IOException {
        String invalid_taskof = "/todos/1/tasksof/3";
        int result = TodoInstance.getStatusCode("DELETE",invalid_taskof);
        assertEquals(TodoInstance.SC_NOT_FOUND, result);
    }

    @Test
    public void testDeleteCategoriesOfProjects() throws IOException, InterruptedException {
        //Need to create a link categories
        JSONObject json = new JSONObject();
        json.put("id", "1");
        TodoInstance.post("/projects/1/categories", json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/projects/1/categories");
        assertEquals(1,response.getJSONArray("categories").length());
        Thread.sleep(500);
        String valid_task = "/projects/1/categories/1";
        TodoInstance.send("DELETE",valid_task);
        Thread.sleep(500);
        response = TodoInstance.send("GET","/projects/1/categories");
        assertEquals(0, response.getJSONArray("categories").length());
    }

    @Test
    public void testDeleteInvalidcategoriesOfProjects() throws IOException {
        String invalid_taskof = "/todos/1/categories/3";
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
