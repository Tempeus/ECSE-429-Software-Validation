package ecse429.group11;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

public class TestProjects {

    //GET Projects
    @Test
    public void testGetProjectStatus() throws IOException {
        String project_url = "/projects";
        assertEquals(TodoInstance.SC_SUCCESS,TodoInstance.getStatusCode(project_url));
    }

    @Test
    public void testGetProjectSize() throws IOException {
        String project_url = "/projects";
        JSONObject response = TodoInstance.send("GET", project_url);
        assertEquals(1,response.getJSONArray("projects").length());
    }

    @Test
    public void testGetProjectTitle() throws IOException {
        String project_url = "/projects";
        JSONObject response = TodoInstance.send("GET", project_url);
        String result = response.getJSONArray("projects").getJSONObject(0).getString("title");
        assertEquals("Office Work", result);
    }

    @Test
    public void testGetProjectCompletionStatus() throws IOException {
        String project_url = "/projects";
        JSONObject response = TodoInstance.send("GET", project_url);
        String result = response.getJSONArray("projects").getJSONObject(0).getString("completed");
        assertEquals("false", result);
    }

    @Test
    public void testGetInvalidProjectTitle() throws IOException {

    }
    //POST Projects

    //HEAD Projects

}
