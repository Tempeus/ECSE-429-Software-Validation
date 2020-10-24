package ecse429.group11;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
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
        //Todo: Go through array and get the index of ID 1
        int id_indx = 0;
        assertEquals("1",response.getJSONArray("projects").getJSONObject(0).getJSONArray("tasks").getJSONObject(id_indx).getString("id"));
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
    public void testCreateValidTodo(){
        String validID = "/todos";
        JSONObject response = new JSONObject();
        response.put("title", "TitleTest");
        response.put("description", "DescriptionTest");
        response.put("doneStatus", "TRUE");

        System.out.println(response.toString());
        //What do i do next? create a function? Use send?
    }

    @Test
    public void testCreateWithOnlyTitle(){
        JSONObject response = new JSONObject();
        response.put("title", "TitleTest");
    }

    @Test
    public void TestCreateWithExistingTitle(){
        JSONObject response = new JSONObject();
        response.put("title", "file paperwork");
    }

    @Test
    public void testCreateInvalidTodo(){
        JSONObject response = new JSONObject();
        response.put("description", "DescriptionTest");
        response.put("doneStatus", "TRUE");
    }

    //POST todos/id
    @Test
    public void testUpdateWithID(){

    }

    @Test
    public void testUpdateTitle(){

    }

    @Test
    public void testUpdateDoneStatus(){

    }

    @Test
    public void testUpdateDescription(){

    }

    //PUT todos/id
    @Test
    public void testOverrideWithID(){

    }

    @Test
    public void testOverrideTitle(){

    }

    @Test
    public void testOverrideDoneStatus(){

    }

    @Test
    public void testOverrideDescription(){

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
