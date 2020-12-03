package ecse429.group11.performance;

import ecse429.group11.restAPI.TodoInstance;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.FileWriter;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TodosPerformanceTest {
    private static long T1StartTime;
    private static long T2StartTime;

    @Test
    public void AtestCreateTodos() throws IOException, InterruptedException {
        FileWriter writer = new FileWriter("Performance.csv",true);
        writer.write("Create Todos\n");

        T1StartTime = System.currentTimeMillis();
        T2StartTime = System.currentTimeMillis();

        String validID = "/todos";
        String title = "TitleTest";
        String description = "DescriptionTest";

        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("description", description);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

        long T2 = System.currentTimeMillis() - T2StartTime - 500;

        JSONObject response = TodoInstance.send("GET", "/todos?title=" + title);
        assertEquals(title, response.getJSONArray("todos").getJSONObject(0).getString("title"));

        long T1 = System.currentTimeMillis() - T1StartTime - 500;
        System.out.println("T2 of Create Todos is: " + T2);
        System.out.println("T1 of Create Todos is: " + T1);

        writer.write("T2," + T2 + "\n");
        writer.write("T1," + T1 + "\n");
        writer.close();
    }

    @Test
    public void BtestChangeTodos() throws IOException, InterruptedException {
        FileWriter writer = new FileWriter("Performance.csv",true);
        writer.write("Change Todos\n");

        T1StartTime = System.currentTimeMillis();

        String title = "TitleTest";
        JSONObject getID = TodoInstance.send("GET", "/todos?title=" + title);
        String id = getID.getJSONArray("todos").getJSONObject(0).get("id").toString();

        T2StartTime = System.currentTimeMillis();

        String validID = "/todos/" + id;
        String description = "DESCRIPTION";

        JSONObject json = new JSONObject();
        json.put("description", description);
        TodoInstance.post(validID,json.toString());
        Thread.sleep(500);

        long T2 = System.currentTimeMillis() - T2StartTime - 500;

        JSONObject response = TodoInstance.send("GET", "/todos/"  + id);
        assertEquals("DESCRIPTION",response.getJSONArray("todos").getJSONObject(0).get("description"));

        long T1 = System.currentTimeMillis() - T1StartTime - 500;
        System.out.println("T2 of Change Todos is: " + T2);
        System.out.println("T1 of Change Todos is: " + T1);

        writer.write("T2," + T2 + "\n");
        writer.write("T1," + T1 + "\n");
        writer.close();
    }

    @Test
    public void CtestDeleteTodos() throws IOException, InterruptedException {
        FileWriter writer = new FileWriter("Performance.csv",true);
        writer.write("Delete Todos\n");

        T1StartTime = System.currentTimeMillis();

        String title = "TitleTest";
        JSONObject getID = TodoInstance.send("GET", "/todos?title=" + title);
        String id = getID.getJSONArray("todos").getJSONObject(0).get("id").toString();

        T2StartTime = System.currentTimeMillis();
        String valid_id = "/todos/" + id;
        assertEquals(TodoInstance.getStatusCode(valid_id), TodoInstance.SC_SUCCESS);
        Thread.sleep(500);

        long T2 = System.currentTimeMillis() - T2StartTime - 500;

        TodoInstance.send("DELETE",valid_id);
        assertEquals(TodoInstance.getStatusCode(valid_id), TodoInstance.SC_NOT_FOUND);

        long T1 = System.currentTimeMillis() - T1StartTime - 500;
        System.out.println("T2 of Delete Todos is: " + T2);
        System.out.println("T1 of Delete Todos is: " + T1);

        writer.write("T2," + T2 + "\n");
        writer.write("T1," + T1 + "\n\n");
        writer.close();
    }
}
