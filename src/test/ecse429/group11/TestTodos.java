package ecse429.group11;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class TestTodos {

    @Before
    public void startInstance(){
        TodoInstance.runApplication();
    }

    @After
    public void killInstance(){
        TodoInstance.killInstance();
    }

    //GET todos
    @Test
    public void testGetStatusCode() throws IOException {
        assertEquals(TodoInstance.getStatusCode("/todos"), TodoInstance.SC_SUCCESS);
    }

    @Test
    public void testGetAllTodos() throws IOException {
        JSONObject response = TodoInstance.send("GET", "/todos");
        assertEquals(2,response.getJSONArray("todos").length());
    }

    @Test
    public void testGetByTitle() throws IOException {
        String valid_title = "/todos?title=file%20paperwork";
        JSONObject response = TodoInstance.send("GET",valid_title);
        String result = response.getJSONArray("todos").getJSONObject(0).getString("title");
        assertEquals(result,"file paperwork");
    }

    @Test
    public void testGetByInvalidTitle() throws IOException {
        String invalid_title = "/todos?title=mothman";
        JSONObject response = TodoInstance.send("GET", invalid_title);
        assertEquals(0,response.getJSONArray("todos").length());
    }

    @Test
    public void testGetByDoneStatusTrue() throws IOException {
        String donestatus_true = "/todos?doneStatus=true";
        JSONObject response = TodoInstance.send("GET", donestatus_true);
        assertEquals(0,response.getJSONArray("todos").length());
    }

    @Test
    public void testGetByDoneStatusFalse() throws IOException {
        String donestatus_true = "/todos?doneStatus=false";
        JSONObject response = TodoInstance.send("GET", donestatus_true);
        assertEquals(2,response.getJSONArray("todos").length());
    }

    @Test
    public void testGetByInvalidDoneStatus() throws IOException {
        String donestatus_invalid = "/todos?doneStatus=maybe";
        JSONObject response = TodoInstance.send("GET", donestatus_invalid);
        assertEquals(0,response.getJSONArray("todos").length());
    }

    @Test
    public void testGetByDescription() throws IOException {
        String description = "/todos?description=";
        JSONObject response = TodoInstance.send("GET", description);
        assertEquals(2,response.getJSONArray("todos").length());
    }

    @Test
    public void testGetByInvalidDescription() throws IOException{
        String description = "/todos?description=NonExistent";
        JSONObject response = TodoInstance.send("GET", description);
        assertEquals(0,response.getJSONArray("todos").length());
    }

    //GET todos/id
    @Test
    public void testGetSpecificTodoUsingID() throws IOException{
        JSONObject response = TodoInstance.send("GET", "/todos/1");
        String expected = "1";
        String result = response.getJSONArray("todos").getJSONObject(0).getString("id");
        assertEquals(result, expected);
    }

    @Test
    public void testGetInvalidID() throws IOException {
        String invalid_request = "/todos/3";
        assertEquals(TodoInstance.getStatusCode(invalid_request), TodoInstance.SC_NOT_FOUND);
    }

    @Test
    public void testGetValidID() throws IOException {
        String valid_request = "/todos/2";
        assertEquals(TodoInstance.getStatusCode(valid_request),TodoInstance.SC_SUCCESS);
    }

    @Test
    public void testGetTaskOfTodo() throws IOException {
        String valid_request = "/todos/1/tasksof";
        assertEquals(TodoInstance.getStatusCode(valid_request),TodoInstance.SC_SUCCESS);
        JSONObject response = TodoInstance.send("GET", valid_request);
        assertEquals(1,response.getJSONArray("projects").length());
        String result = response.getJSONArray("projects").getJSONObject(0).getJSONArray("tasks").getJSONObject(0).getString("id");
        assertEquals(true, result.equals("2") || result.equals("1"));
    }

    @Test
    public void testGetInvalidTaskOfTodo() throws IOException {
        String invalid_taskof = "/todos/1/tasksof/3";
        int result = TodoInstance.getStatusCode(invalid_taskof);
        assertEquals(TodoInstance.SC_NOT_FOUND, result);
    }

    @Test
    public void testGetCategoriesOfTodo() throws IOException {
        String valid_request = "/todos/1/categories";
        JSONObject response = TodoInstance.send("GET",valid_request);
        assertEquals(1,response.getJSONArray("categories").length());
        assertEquals("1",response.getJSONArray("categories").getJSONObject(0).getString("id"));
        assertEquals("Office",response.getJSONArray("categories").getJSONObject(0).getString("title"));

    }

    @Test
    public void testGetInvalidCategoriesOfTodo() throws IOException {
        String invalid_categories = "/todos/1/categories/3";
        int result = TodoInstance.getStatusCode(invalid_categories);
        assertEquals(TodoInstance.SC_NOT_FOUND, result);
    }

    //POST todos
    @Test
    public void testCreateValidTodo() throws IOException, InterruptedException {
        String validID = "/todos";
        String title = "TitleTest";
        String description = "DescriptionTest";
        boolean donestatus = true;

        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("description", description);
        json.put("doneStatus", donestatus);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(200);

        JSONObject response = TodoInstance.send("GET", "/todos");
        System.out.println(response);
        assertEquals(3,response.getJSONArray("todos").length());
    }

    @Test
    public void testCreateWithOnlyTitle() throws IOException, InterruptedException {
        String validID = "/todos";
        String title = "TitleTest";

        JSONObject json = new JSONObject();
        json.put("title", title);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/todos");
        System.out.println(response);
        assertEquals(3,response.getJSONArray("todos").length());
    }

    @Test
    public void TestCreateWithExistingTitle() throws IOException, InterruptedException {
        String validID = "/todos";
        String title = "file paperwork";

        JSONObject json = new JSONObject();
        json.put("title", title);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/todos");
        assertEquals(3,response.getJSONArray("todos").length());
    }

    @Test
    public void testCreateInvalidTodo() throws IOException {
        JSONObject json = new JSONObject();
        json.put("description", "DescriptionTest");
        json.put("doneStatus", "TRUE");
        TodoInstance.post("/todos", json.toString());

        JSONObject response = TodoInstance.send("GET", "/todos");
        assertEquals(2,response.getJSONArray("todos").length());
    }

    @Test
    public void testCreateCategoriesOfTodo() throws IOException {
        JSONObject json = new JSONObject();
        json.put("id", "1");
        TodoInstance.post("/todos/1/categories", json.toString());

        JSONObject response = TodoInstance.send("GET", "/todos/1/categories");
        assertEquals(1,response.getJSONArray("categories").length());
    }

    @Test
    public void testCreateTasksOfTodo() throws IOException {
        JSONObject json = new JSONObject();
        json.put("id", "1");
        TodoInstance.post("/todos/1/tasksof", json.toString());

        JSONObject response = TodoInstance.send("GET", "/todos/1/tasksof");
        assertEquals(1,response.getJSONArray("projects").length());
    }

    //POST todos/id

    @Test
    public void testUpdateTitle() throws IOException, InterruptedException {
        String validID = "/todos/1";
        String title = "NEWTITLE";

        JSONObject json = new JSONObject();
        json.put("title", title);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/todos/1");
        assertEquals(title,response.getJSONArray("todos").getJSONObject(0).get("title"));
    }

    @Test
    public void testUpdateDoneStatus() throws IOException, InterruptedException {
        String validID = "/todos/1";
        boolean donestatus = true;

        JSONObject json = new JSONObject();
        json.put("doneStatus", donestatus);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/todos/1");
        assertEquals("true",response.getJSONArray("todos").getJSONObject(0).get("doneStatus"));
    }

    @Test
    public void testUpdateDescription() throws IOException, InterruptedException {
        String validID = "/todos/1";
        String description = "DESCRIPTION";

        JSONObject json = new JSONObject();
        json.put("description", description);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/todos/1");
        assertEquals("DESCRIPTION",response.getJSONArray("todos").getJSONObject(0).get("description"));
    }

    //PUT todos/id
    @Test
    public void testOverrideTitle() throws IOException, InterruptedException {
        String validID = "/todos/1";
        String title = "NEWTITLE";

        JSONObject json = new JSONObject();
        json.put("title", title);
        TodoInstance.put(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/todos/1");
        assertEquals(title,response.getJSONArray("todos").getJSONObject(0).get("title"));
        boolean NotExist = false;
        try{
            response.getJSONArray("todos").getJSONObject(0).get("categories");
            response.getJSONArray("todos").getJSONObject(0).get("tasksof");
        } catch(JSONException e) {
            NotExist = true;
        }

        assertEquals(true,NotExist);
    }

    @Test
    public void testOverrideDoneStatus() throws IOException, InterruptedException {
        String validID = "/todos/1";
        String title = "NEWTITLE";
        boolean donestatus = true;

        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("doneStatus", donestatus);
        TodoInstance.put(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/todos/1");
        assertEquals(title,response.getJSONArray("todos").getJSONObject(0).get("title"));
        assertEquals("true",response.getJSONArray("todos").getJSONObject(0).get("doneStatus"));
        boolean NotExist = false;
        try{
            response.getJSONArray("todos").getJSONObject(0).get("categories");
            response.getJSONArray("todos").getJSONObject(0).get("tasksof");
        } catch(JSONException e) {
            NotExist = true;
        }

        assertEquals(true,NotExist);
    }

    @Test
    public void testOverrideDescription() throws IOException, InterruptedException {
        String validID = "/todos/1";
        String title = "NEWTITLE";
        String description = "DESCRIPTION";

        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("description", description);
        TodoInstance.put(validID,json.toString());
        Thread.sleep(500);

        JSONObject response = TodoInstance.send("GET", "/todos/1");
        assertEquals(title,response.getJSONArray("todos").getJSONObject(0).get("title"));
        assertEquals("DESCRIPTION",response.getJSONArray("todos").getJSONObject(0).get("description"));
        boolean NotExist = false;
        try{
            response.getJSONArray("todos").getJSONObject(0).get("categories");
            response.getJSONArray("todos").getJSONObject(0).get("tasksof");
        } catch(JSONException e) {
            NotExist = true;
        }

        assertEquals(true,NotExist);
    }

    //DELETE todos
    @Test
    public void testDeleteAllTodos() throws IOException {
        String option = "/todos";
        assertEquals(TodoInstance.SC_UNSUPPORTED,TodoInstance.getStatusCode("DELETE", option));
    }

    //DELETE todos/id

    @Test
    public void testDeleteValidTodo() throws IOException {
        String valid_id = "/todos/1";
        assertEquals(TodoInstance.getStatusCode(valid_id), TodoInstance.SC_SUCCESS);
        TodoInstance.send("DELETE",valid_id);
        assertEquals(TodoInstance.getStatusCode(valid_id), TodoInstance.SC_NOT_FOUND);
    }

    @Test
    public void testDeleteInvalidTodo() throws IOException {
        String invalid_id = "/todos/3";

        JSONObject result = TodoInstance.send("DELETE",invalid_id);
        assertEquals(null, result);
    }

    //Delete relationship between projects and todos
    @Test
    public void testDeleteTaskOfTodo() throws IOException {
        String valid_taskof = "/todos/1/tasksof/1";
        TodoInstance.send("DELETE",valid_taskof);
        JSONObject response = TodoInstance.send("GET","/todos/1/tasksof");
        assertEquals(0,response.getJSONArray("projects").length());
    }

    @Test
    public void testDeleteInvalidTaskOfTodo() throws IOException {
        String invalid_taskof = "/todos/1/tasksof/3";
        int result = TodoInstance.getStatusCode("DELETE",invalid_taskof);
        assertEquals(TodoInstance.SC_NOT_FOUND, result);
    }

    @Test
    public void testDeleteValidCategoriesOfTodo() throws IOException {
        String valid_categories = "/todos/1/categories/1";
        TodoInstance.send("DELETE",valid_categories);
        JSONObject response = TodoInstance.send("GET","/todos/1/categories");
        assertEquals(0,response.getJSONArray("categories").length());
    }

    @Test
    public void testDeleteInvalidCategoriesOfTodo() throws IOException {
        String invalid_categories = "/todos/1/categories/3";
        int result = TodoInstance.getStatusCode("DELETE",invalid_categories);
        assertEquals(TodoInstance.SC_NOT_FOUND, result);
    }

    //HEAD todos
    @Test
    public void testHeadTodos() throws IOException {
        String option = "/todos";
        String head = TodoInstance.getHeadContentType(option);
        assertEquals("application/json", head);
    }

    @Test
    public void testHeadTodosID() throws IOException{
        String option = "/todos/1";
        String head = TodoInstance.getHeadContentType(option);
        assertEquals("application/json", head);
    }

    @Test
    public void testHeadTodosTasksOf() throws IOException{
        String option = "/todos/1/tasksof";
        String head = TodoInstance.getHeadContentType(option);
        assertEquals("application/json", head);
    }

    @Test
    public void testHeadTodosCategories() throws IOException{
        String option = "/todos/1/categories";
        String head = TodoInstance.getHeadContentType(option);
        assertEquals("application/json", head);
    }

}
