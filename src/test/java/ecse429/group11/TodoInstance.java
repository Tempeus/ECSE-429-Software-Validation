package ecse429.group11;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TodoInstance {

    //List of status codes that will be referred during the UNIT TESTING
    public static final int SC_SUCCESS = 200;
    public static final int SC_CREATED = 201;
    public static final int SC_NOT_FOUND = 404;
    public static final int SC_BAD_REQUEST = 400;

    private static final String baseURL = "http://localhost:4567";

    public static void runApplication(){
        try {
            Process process = Runtime.getRuntime().exec("java -jar runTodoManagerRestAPI-1.5.5.jar");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void killInstance(){
        try {
            send("GET","/shutdown");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public static int getStatusCode(String option) throws IOException {
        URL url = new URL(baseURL + option);
        HttpURLConnection http;
        http = (HttpURLConnection)url.openConnection();
        return http.getResponseCode();
    }
}
