import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class Main {
    private static final String baseURL = "http://localhost:4567";
    public static void main(String[] args) throws IOException {
//        JSONObject response;
//        response = send("GET");
//        System.out.println(response);
//        System.out.println("plz work");
    }

//    public static JSONObject send(String type) throws IOException {
//        URL url = new URL(baseURL + "/todos");
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod(type);
//        connection.setRequestProperty("Accept", "application/json");
//        System.out.println(connection.getContentType());
//        if (connection.getResponseCode() != 200) {
//            throw new RuntimeException(url.toString() + " Returned error: " + connection.getResponseCode());
//        }
//        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
//        String response = br.readLine();
//        if (response == null) {
//            return null;
//        }
//
//        JSONObject json = new JSONObject(response);
//        connection.disconnect();
//        return json;
//    }
}
