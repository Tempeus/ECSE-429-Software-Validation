package ecse429.group11;

import static ecse429.group11.TodoInstance.send;
import static org.junit.Assert.assertEquals;


import io.restassured.internal.mapping.JsonbMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

public class TestTodos {

//    @Before
//    public void startInstance(){
//        TodoInstance.runApplication();
//    }

//    @After
//    public void deadifyInstance(){
//        TodoInstance.killInstance();
//    }

    //GET todos
    @Test
    public void testGetStatusCode() throws IOException {
        assertEquals(TodoInstance.getStatusCode("/todos"), TodoInstance.SC_SUCCESS);
    }

    @Test
    public void testGetAllTodos() throws IOException {
        JSONObject response = send("GET", "/todos");
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
        JSONObject response = send("GET", invalid_title);
        assertEquals(0,response.getJSONArray("todos").length());
    }

    @Test
    public void testGetByDoneStatusTrue() throws IOException {
        String donestatus_true = "/todos?doneStatus=true";
        JSONObject response = send("GET", donestatus_true);
        assertEquals(0,response.getJSONArray("todos").length());
    }

    @Test
    public void testGetByDoneStatusFalse() throws IOException {
        String donestatus_true = "/todos?doneStatus=false";
        JSONObject response = send("GET", donestatus_true);
        assertEquals(2,response.getJSONArray("todos").length());
    }

    @Test
    public void testGetByInvalidDoneStatus() throws IOException {
        String donestatus_invalid = "/todos?doneStatus=maybe";
        JSONObject response = send("GET", donestatus_invalid);
        assertEquals(0,response.getJSONArray("todos").length());
    }

    @Test
    public void testGetByDescription() throws IOException {
        String description = "/todos?description=";
        JSONObject response = send("GET", description);
        assertEquals(2,response.getJSONArray("todos").length());
    }

    @Test
    public void testGetByInvalidDescription() throws IOException{
        String description = "/todos?description=NonExistent";
        JSONObject response = send("GET", description);
        assertEquals(0,response.getJSONArray("todos").length());
    }

    //GET todos/id
    @Test
    public void testGetSpecificTodoUsingID() throws IOException{
        JSONObject response = send("GET", "/todos/1");
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

    //POST todos
    @Test
    public void testCreateValidTodo(){
        String validID = "/todos";
    }

    @Test
    public void testCreateWithOnlyTitle(){

    }

    @Test
    public void TestCreateWithExistingTitle(){

    }

    @Test
    public void testCreateInvalidTodo(){

    }

    //POST todos/id
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

    //HEAD todos

    //HEAD todos/id




}
