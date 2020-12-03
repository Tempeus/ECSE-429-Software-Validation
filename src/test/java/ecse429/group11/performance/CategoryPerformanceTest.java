package ecse429.group11.performance;

import ecse429.group11.restAPI.TodoInstance;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CategoryPerformanceTest {

    private static long T1StartTime;
    private static long T2StartTime;

    @Test
    public void testCreateCategories() throws IOException, InterruptedException {
       T1StartTime = System.currentTimeMillis();
       T2StartTime = System.currentTimeMillis();

       String validID = "/categories";
       String title = "TitleTest";
       String description = "DescriptionTest";

       JSONObject json = new JSONObject();
       json.put("title", title);
       json.put("description", description);
       TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

       long T2 = System.currentTimeMillis() - T2StartTime - 500;
       System.out.println("T2 of Create Category is: " + T2);

       JSONObject response = TodoInstance.send("GET", "/categories?title=" + title);
       assertEquals(title, response.getJSONArray("categories").getJSONObject(0).getString("title"));

       long T1 = System.currentTimeMillis() - T1StartTime - 500;
        System.out.println("T1 of Create Category is: " + T1);
    }

    @Test
    public void testChangeCategories() throws IOException, InterruptedException {
        T1StartTime = System.currentTimeMillis();

        String title = "TitleTest";
        JSONObject getID = TodoInstance.send("GET", "/categories?title=" + title);
        String id = getID.getJSONArray("categories").getJSONObject(0).get("id").toString();

        T2StartTime = System.currentTimeMillis();

        String validID = "/categories/" + id;
        String description = "DESCRIPTION";

        JSONObject json = new JSONObject();
        json.put("description", description);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

        long T2 = System.currentTimeMillis() - T2StartTime - 500;
        System.out.println("T2 of Change Category is: " + T2);

        JSONObject response = TodoInstance.send("GET", "/categories/"  + id);
        assertEquals("DESCRIPTION",response.getJSONArray("categories").getJSONObject(0).get("description"));

        long T1 = System.currentTimeMillis() - T1StartTime - 500;
        System.out.println("T1 of Change Category is: " + T1);
    }

    @Test
    public void testDeleteCategories() throws IOException, InterruptedException {
        T1StartTime = System.currentTimeMillis();

        String title = "TitleTest";
        JSONObject getID = TodoInstance.send("GET", "/categories?title=" + title);
        String id = getID.getJSONArray("categories").getJSONObject(0).get("id").toString();

        T2StartTime = System.currentTimeMillis();
        String valid_id = "/categories/" + id;
        assertEquals(TodoInstance.getStatusCode(valid_id), TodoInstance.SC_SUCCESS);
        Thread.sleep(500);

        long T2 = System.currentTimeMillis() - T2StartTime - 500;
        System.out.println("T2 of Delete Category is: " + T2);

        TodoInstance.send("DELETE",valid_id);
        assertEquals(TodoInstance.getStatusCode(valid_id), TodoInstance.SC_NOT_FOUND);

        long T1 = System.currentTimeMillis() - T1StartTime - 500;
        System.out.println("T1 of Delete Category is: " + T1);
    }
}
