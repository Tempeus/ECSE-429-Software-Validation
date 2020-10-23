package ecse429.group11;

import static ecse429.group11.TodoInstance.send;
import static org.junit.Assert.assertEquals;


import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

public class TestTodos {




//    @Before
//    public void startInstance(){
//        TodoInstance.runApplication();
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
    public void testCreateTodo(){

    }
    //HEAD todos


//    @After
//    public void deadifyInstance(){
//        TodoInstance.killInstance();
//    }

}
