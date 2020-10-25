package ecse429.group11;

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
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        connection.setDoOutput(true);
        byte[] input = JSONInputString.getBytes("utf-8");
        connection.setFixedLengthStreamingMode(input.length);
        connection.connect();
        try(OutputStream os = connection.getOutputStream()) {
            os.write(input);
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

    public static String JSONConvert(JSON json[]){
        StringBuilder result = new StringBuilder();
        result.append("{");
        int i;
        for(i = 0; i < json.length - 1; i++){
            result.append("\"" + json[i].key + "\": \"" + json[i].value + "\",");
        }
        result.append("\"" + json[i].key + "\": \"" + json[i].value + "\"");
        result.append("}");

        return result.toString();
    }

    public static void main(String[] args){
        JSON[] json = new JSON[3];
        json[0] = new JSON("password", "maga2020");
        json[1] = new JSON("title", "faketitlel");
        json[2] = new JSON("title", "faketitlel");

        System.out.println(JSONConvert(json));
    }
}
