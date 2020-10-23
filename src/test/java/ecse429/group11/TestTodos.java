package ecse429.group11;

import static ecse429.group11.TodoInstance.send;
import static org.junit.Assert.assertEquals;


import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class TestTodos {

    //List of status codes that will be referred during the UNIT TESTING
    private static final int SUCCESS = 200;
    private static final int CREATED = 201;
    private static final int NOT_FOUND = 404;
    private static final int BAD_REQUEST = 400;


//    @Before
//    public void startInstance(){
//        TodoInstance.runApplication();
//    }



    //GET todos
    @Test
    public void testGetStatusCode() throws IOException {
        assertEquals(TodoInstance.getStatusCode("/todos"), SUCCESS);
    }

    @Test
    public void testGetAllTodos() throws IOException {
        JSONObject response = send("GET", "/todos");
        assertEquals(2,response.getJSONArray("todos").length());
    }

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
        assertEquals(TodoInstance.getStatusCode(invalid_request), NOT_FOUND);
    }

    @Test
    public void testGetValidID() throws IOException {
        String valid_request = "/todos/2";
        assertEquals(TodoInstance.getStatusCode(valid_request),SUCCESS);
    }





    //POST todos

    @Test
    public void testCreateTodo(){

    }
    //HEAD todos


//    @After
//    public void deadifyInstance(){
//        TodoInstance.killInstance();
//    }

}
