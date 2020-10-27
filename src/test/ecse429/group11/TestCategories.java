package ecse429.group11;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.JVM)
public class TestCategories {


    @Before
    public void startInstance(){
        TodoInstance.runApplication();
    }

    @After
    public void killInstance(){
        TodoInstance.killInstance();
    }

    //GET categories
    @Test
    public void testGetStatusCode() throws IOException {
        assertEquals(TodoInstance.getStatusCode("/categories"), TodoInstance.SC_SUCCESS);
    }

    @Test
    public void testGetAllCategories() throws IOException {
        JSONObject response = TodoInstance.send("GET", "/categories");
        assertEquals(2,response.getJSONArray("categories").length());
    }

    @Test
    public void testGetCategoriesTitle() throws IOException {
        String categories_url = "/categories";
        JSONObject response = TodoInstance.send("GET", categories_url);
        String result = response.getJSONArray("categories").getJSONObject(0).getString("title");
        assertEquals(result.equals("Office") || result.equals("Home"), true);
    }

    @Test
    public void testGetCategoriesDescription() throws IOException {
        String categories_url = "/categories";
        JSONObject response = TodoInstance.send("GET", categories_url);
        String result = response.getJSONArray("categories").getJSONObject(0).getString("description");
        assertEquals("", result);
    }

    @Test
    public void testForwardSlashInstability() throws IOException {
        assertEquals(TodoInstance.SC_NOT_FOUND, TodoInstance.getStatusCode("/todos/"));
    }

    //GET categories/id

    @Test
    public void testGetSpecificCategoryUsingID() throws IOException {
        JSONObject response = TodoInstance.send("GET", "/categories/1");
        String expected = "1";
        String result = response.getJSONArray("categories").getJSONObject(0).getString("id");
        assertEquals(result, expected);
    }

    @Test
    public void testGetCategoriesWithValidID() throws IOException {
        String valid_request = "/categories/1";
        assertEquals(TodoInstance.getStatusCode(valid_request), TodoInstance.SC_SUCCESS);
    }

    @Test
    public void testGetCategoriesWithInvalidID() throws IOException {
        String invalid_request = "/categories/3";
        assertEquals(TodoInstance.getStatusCode(invalid_request), TodoInstance.SC_NOT_FOUND);
    }

    @Test
    public void testGetProjectsOfCategories() throws IOException {
        String valid_request = "/categories/1/projects";
        assertEquals(TodoInstance.getStatusCode(valid_request),TodoInstance.SC_SUCCESS);
        JSONObject response = TodoInstance.send("GET", valid_request);
        assertEquals(0,response.getJSONArray("projects").length());
    }

    @Test
    public void testGetInvalidProjectOfCategories() throws IOException {
        String invalid_request = "/categories/1/projects/1";
        assertEquals(TodoInstance.getStatusCode(invalid_request), TodoInstance.SC_NOT_FOUND);
    }

    @Test
    public void testGetTodoOfCategories() throws IOException {
        String valid_request = "/categories/1/todos";
        assertEquals(TodoInstance.getStatusCode(valid_request),TodoInstance.SC_SUCCESS);
        JSONObject response = TodoInstance.send("GET", valid_request);
        assertEquals(0,response.getJSONArray("todos").length());
    }

    @Test
    public void testGetInvalidTodoOfCategories() throws IOException {
        String invalid_request = "/categories/1/todos/1";
        assertEquals(TodoInstance.getStatusCode(invalid_request), TodoInstance.SC_NOT_FOUND);
    }


    //POST categories

    @Test
    public void testCreateValidCategories() throws IOException, InterruptedException {
        String validID = "/categories";
        String title = "TitleTest";
        String description = "DescriptionTest";

        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("description", description);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(200);

        JSONObject response = TodoInstance.send("GET", "/categories");
        System.out.println(response);
        assertEquals(3,response.getJSONArray("categories").length());
    }

    @Test
    public void testCreateWithOnlyTitle() throws IOException, InterruptedException {
        String validID = "/categories";
        String title = "TitleTest";

        JSONObject json = new JSONObject();
        json.put("title", title);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/categories");
        System.out.println(response);
        assertEquals(3,response.getJSONArray("categories").length());
    }

    @Test
    public void testCreateWithExistingTitle() throws IOException, InterruptedException {
        String validID = "/categories";
        String title = "Office";

        JSONObject json = new JSONObject();
        json.put("title", title);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(200);

        JSONObject response = TodoInstance.send("GET", "/categories");
        assertEquals(3,response.getJSONArray("categories").length());
    }

    @Test
    public void testCreateInvalidCategories() throws IOException {
        JSONObject json = new JSONObject();
        json.put("description", "DescriptionTest");
        TodoInstance.post("/categories", json.toString());

        JSONObject response = TodoInstance.send("GET", "/categories");
        assertEquals(2,response.getJSONArray("categories").length());
    }

    @Test
    public void testCreateTodoOfCategories() throws IOException, InterruptedException {
        JSONObject json = new JSONObject();
        json.put("id", "1");
        TodoInstance.post("/categories/1/todos", json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/categories/1/todos");
        assertEquals(1,response.getJSONArray("todos").length());
    }

    @Test
    public void testCreateProjectsOfCategories() throws IOException, InterruptedException {
        JSONObject json = new JSONObject();
        json.put("id", "1");
        TodoInstance.post("/categories/1/projects", json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/categories/1/projects");
        assertEquals(1,response.getJSONArray("projects").length());
    }


    //POST categories/id
    @Test
    public void testUpdateTitle() throws IOException, InterruptedException {
        String validID = "/categories/1";
        String title = "NEWTITLE";

        JSONObject json = new JSONObject();
        json.put("title", title);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/categories/1");
        assertEquals(title,response.getJSONArray("categories").getJSONObject(0).get("title"));
    }

    @Test
    public void testUpdateDescription() throws IOException, InterruptedException {
        String validID = "/categories/1";
        String description = "DESCRIPTION";

        JSONObject json = new JSONObject();
        json.put("description", description);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/categories/1");
        assertEquals("DESCRIPTION",response.getJSONArray("categories").getJSONObject(0).get("description"));
    }

    //PUT categories/id
    @Test
    public void testOverrideTitle() throws IOException, InterruptedException {
        String validID = "/categories/1";
        String title = "NEWTITLE";

        JSONObject json = new JSONObject();
        json.put("title", title);
        TodoInstance.put(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/categories/1");
        assertEquals(title,response.getJSONArray("categories").getJSONObject(0).get("title"));
    }

    @Test
    public void testOverrideDescription() throws IOException, InterruptedException {
        String validID = "/categories/1";
        String title = "NEWTITLE";
        String description = "DESCRIPTION";

        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("description", description);
        TodoInstance.put(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/categories/1");
        assertEquals(title,response.getJSONArray("categories").getJSONObject(0).get("title"));
        assertEquals("DESCRIPTION",response.getJSONArray("categories").getJSONObject(0).get("description"));
    }

    //DELETE categories
    @Test
    public void testDeleteAllCategories() throws IOException {
        String option = "/categories";
        assertEquals(TodoInstance.SC_UNSUPPORTED,TodoInstance.getStatusCode("DELETE", option));
    }

    //DELETE categories/id
    @Test
    public void testDeleteValidCategories() throws IOException {
        String valid_id = "/categories/1";
        assertEquals(TodoInstance.getStatusCode(valid_id), TodoInstance.SC_SUCCESS);
        TodoInstance.send("DELETE",valid_id);
        assertEquals(TodoInstance.getStatusCode(valid_id), TodoInstance.SC_NOT_FOUND);
    }

    @Test
    public void testDeleteInvalidCategories() throws IOException {
        String invalid_id = "/categories/3";

        JSONObject result = TodoInstance.send("DELETE",invalid_id);
        assertEquals(null, result);
    }

    @Test
    public void testDeleteProjectsOfCategories() throws IOException {
        String valid_task = "/projects/1/tasks/1";
        TodoInstance.send("DELETE",valid_task);
        JSONObject response = TodoInstance.send("GET","/projects/1/tasks");
        response.getJSONArray("todos").getJSONObject(0).getJSONArray("tasksof");
        assertEquals(1, response.length());
    }

    @Test
    public void testDeleteInvalidProjectsOfCategories() throws IOException {
        String invalid_projects = "/categories/1/projects/3";
        int result = TodoInstance.getStatusCode("DELETE",invalid_projects);
        assertEquals(TodoInstance.SC_NOT_FOUND, result);
    }

    @Test
    public void testDeleteTodosOfCategories() throws IOException {
        String valid_task = "/categories/1/todos/1";
        TodoInstance.send("DELETE",valid_task);
        JSONObject response = TodoInstance.send("GET","/projects/1/tasks");
        response.getJSONArray("todos").getJSONObject(0).getJSONArray("tasksof");
        assertEquals(1, response.length());
    }

    @Test
    public void testDeleteInvalidTodosOfCategories() throws IOException {
        String invalid_todos = "/categories/1/todos/3";
        int result = TodoInstance.getStatusCode("DELETE",invalid_todos);
        assertEquals(TodoInstance.SC_NOT_FOUND, result);
    }

    //HEAD categories
    @Test
    public void testHeadCategories() throws IOException {
        String option = "/categories";
        String head = TodoInstance.getHeadContentType(option);
        assertEquals("application/json", head);
    }

    @Test
    public void testHeadCategoriesID() throws IOException {
        String option = "/categories/1";
        String head = TodoInstance.getHeadContentType(option);
        assertEquals("application/json", head);
    }

    @Test
    public void testHeadCategoriesProjects() throws IOException {
        String option = "/categories/1/projects";
        String head = TodoInstance.getHeadContentType(option);
        assertEquals("application/json", head);
    }

    @Test
    public void testHeadCategoriesTodos() throws IOException {
        String option = "/categories/1/todos";
        String head = TodoInstance.getHeadContentType(option);
        assertEquals("application/json", head);
    }
}
