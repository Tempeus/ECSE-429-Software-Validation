package java.ecse429.group11.restAPI;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

    public static Process ps;

    public static void runApplication(){
        final Runtime re = Runtime.getRuntime();
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "../runTodoManagerRestAPI-1.5.5.jar");
        try {
            ps = pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void killInstance(){
        ps.destroy();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
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

    public static boolean post(String option, String JSONInputString) throws IOException {
        URL url = new URL(baseURL + option);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        byte[] input = JSONInputString.getBytes("utf-8");
        connection.setFixedLengthStreamingMode(input.length);
        connection.connect();
        try(OutputStream os = connection.getOutputStream()) {
            os.write(input,0,input.length);
        }

        return true;
    }

    public static boolean put(String option, String JSONInputString) throws IOException {
        URL url = new URL(baseURL + option);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        byte[] input = JSONInputString.getBytes("utf-8");
        connection.setFixedLengthStreamingMode(input.length);
        connection.connect();
        try(OutputStream os = connection.getOutputStream()) {
            os.write(input,0,input.length);
        }

        return true;
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

//    public static void main(String[] args) throws IOException {
//
//        JSONObject test = new JSONObject();
//        test.put("title", "test1");
//        test.put("doneStatus", true);
//
//        TodoInstance.post("/todos",test.toString());
//    }
}
