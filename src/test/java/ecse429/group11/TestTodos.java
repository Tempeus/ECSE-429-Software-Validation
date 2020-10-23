package ecse429.group11;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestTodos {

    //List of status codes that will be referred during the UNIT TESTING
    private static final int SUCCESS = 200;
    private static final int CREATED = 201;
    private static final int NOT_FOUND = 404;
    private static final int BAD_REQUEST = 400;
    private static final String baseURL = "http://localhost:4567";

//    @Before
//    public void startInstance(){
//        TodoInstance.runApplication();
//    }



    //GET todos
    @Test
    public void testGetStatusCode() throws IOException {
        URL url = new URL("http://localhost:4567/todos");
        HttpURLConnection http;
        http = (HttpURLConnection)url.openConnection();
        int statusCode = -1;
        statusCode = http.getResponseCode();
        System.out.println(statusCode);
        assertEquals(statusCode, SUCCESS);
    }

    @Test
    public void testGetAllTodos() throws IOException {
        JSONObject response = send("GET", "/todos");
        assertEquals(2,response.getJSONArray("todos").length());
        System.out.println(response.getJSONArray("todos").length());
    }

    @Test
    public void testGetSpecificTodoUsingID() throws IOException{
        JSONObject response = send("GET", "/todos/1");

        String result = response.todos[0];
        System.out.println(result);
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

    public static JSONObject send(String type, String option) throws IOException {
        URL url = new URL(baseURL + option);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(type);
        connection.setRequestProperty("Accept", "application/json");
        System.out.println(connection.getContentType());
        if (connection.getResponseCode() != 200) {
            throw new RuntimeException(url.toString() + " Returned error: " + connection.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        String response = br.readLine();
        if (response == null) {
            return null;
        }

        JSONObject json = new JSONObject(response);
        connection.disconnect();
        return json;
    }

}
