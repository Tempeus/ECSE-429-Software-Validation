package ecse429.group11;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class TodoInstance {

    private static Process process;

    //List of status codes that will be referred during the UNIT TESTING
    public static final int SC_SUCCESS = 200;
    public static final int SC_CREATED = 201;
    public static final int SC_NOT_FOUND = 404;
    public static final int SC_UNSUPPORTED = 405;
    public static final int SC_BAD_REQUEST = 400;

    private static final String baseURL = "http://localhost:4567";

    public static void runApplication(){
        final Runtime re = Runtime.getRuntime();
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "../runTodoManagerRestAPI-1.5.5.jar");
        try {
            Process ps = pb.start();
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
        System.out.println(connection.getResponseCode());
        if (connection.getResponseCode() != 200) {
            return null;
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

    public static boolean post(){
        return false;
    }

    public static String getHeadContentType(String option) throws IOException {
        URL url = new URL(baseURL + option);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD");
        connection.setRequestProperty("Accept", "application/json");
        return (connection.getContentType());
    }

    public static int getStatusCode(String type, String option) throws IOException {
        URL url = new URL(baseURL + option);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(type);
        connection.setRequestProperty("Accept", "application/json");
        return connection.getResponseCode();
    }

    public static int getStatusCode(String option) throws IOException {
        URL url = new URL(baseURL + option);
        HttpURLConnection http;
        http = (HttpURLConnection)url.openConnection();
        return http.getResponseCode();
    }
}
