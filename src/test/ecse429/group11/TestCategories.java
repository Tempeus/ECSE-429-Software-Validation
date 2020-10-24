package ecse429.group11;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestCategories {

    @Before
    public void startInstance(){
        TodoInstance.runApplication();
    }

    //GET categories
    @Test
    public void testGetStatusCode() throws IOException {
        assertEquals(TodoInstance.getStatusCode("/categories"), TodoInstance.SC_SUCCESS);
    }

    @Test
    public void testGetAllCategories() throws IOException {
        JSONObject response = TodoInstance.send("GET", "/categories");
        assertEquals(2,response.getJSONArray("categories").length());
    }

    @Test
    public void testGetCategoriesTitle() throws IOException {
        String categories_url = "/categories";
        JSONObject response = TodoInstance.send("GET", categories_url);
        String result = response.getJSONArray("categories").getJSONObject(0).getString("title");
        assertEquals("Office", result);
    }

    @Test
    public void testGetCategoriesDescription() throws IOException {
        String categories_url = "/categories";
        JSONObject response = TodoInstance.send("GET", categories_url);
        String result = response.getJSONArray("categories").getJSONObject(0).getString("description");
        assertEquals("", result);
    }

    //GET categories/id

    @Test
    public void testGetSpecificCategoryUsingID() throws IOException {
        JSONObject response = TodoInstance.send("GET", "/categories/1");
        String expected = "1";
        String result = response.getJSONArray("categories").getJSONObject(0).getString("id");
        assertEquals(result, expected);
    }

    @Test
    public void testGetCategoriesWithValidID() throws IOException {
        String valid_request = "/categories/1";
        assertEquals(TodoInstance.getStatusCode(valid_request), TodoInstance.SC_SUCCESS);
    }

    @Test
    public void testGetCategoriesWithInvalidID() throws IOException {
        String invalid_request = "/categories/3";
        assertEquals(TodoInstance.getStatusCode(invalid_request), TodoInstance.SC_NOT_FOUND);
    }

    @Test
    public void testGetProjectsOfCategories(){

    }

    @Test
    public void testGetInvalidProjectOfCategories(){

    }

    @Test
    public void testGetTodoOfCategories(){

    }


    //POST categories

    @Test
    public void testCreateCategoryWithTitle(){

    }

    @Test
    public void testCreateCategoryWithTitleAndDescription(){

    }

    @Test
    public void testFailCreateCategoryNoTitle(){

    }

    //DELETE categories
    @Test
    public void testDeleteAllCategories() throws IOException {
        String option = "/categories";
        assertEquals(TodoInstance.SC_UNSUPPORTED,TodoInstance.getStatusCode("DELETE", option));
    }

    //DELETE categories/id
    @Test
    public void testDeleteValidCategories() throws IOException {
        String valid_id = "/categories/1";
        assertEquals(TodoInstance.getStatusCode(valid_id), TodoInstance.SC_SUCCESS);
        TodoInstance.send("DELETE",valid_id);
        assertEquals(TodoInstance.getStatusCode(valid_id), TodoInstance.SC_NOT_FOUND);
    }

    @Test
    public void testDeleteInvalidCategories() throws IOException {
        String invalid_id = "/categories/3";

        JSONObject result = TodoInstance.send("DELETE",invalid_id);
        assertEquals(null, result);
    }

    @Test
    public void testDeleteProjectsOfCategories(){

    }

    @Test
    public void testDeleteInvalidProjectsOfCategories() throws IOException {
        String invalid_projects = "/categories/1/projects/3";
        int result = TodoInstance.getStatusCode("DELETE",invalid_projects);
        assertEquals(TodoInstance.SC_NOT_FOUND, result);
    }

    @Test
    public void testDeleteTodosOfCategories(){

    }

    @Test
    public void testDeleteInvalidTodosOfCategories() throws IOException {
        String invalid_todos = "/categories/1/todos/3";
        int result = TodoInstance.getStatusCode("DELETE",invalid_todos);
        assertEquals(TodoInstance.SC_NOT_FOUND, result);
    }

    //HEAD categories
    @Test
    public void testHeadCategories() throws IOException {
        String option = "/categories";
        String head = TodoInstance.getHeadContentType(option);
        assertEquals("application/json", head);
    }

    @Test
    public void testHeadCategoriesID() throws IOException {
        String option = "/categories/1";
        String head = TodoInstance.getHeadContentType(option);
        assertEquals("application/json", head);
    }

    @Test
    public void testHeadCategoriesProjects() throws IOException {
        String option = "/categories/1/projects";
        String head = TodoInstance.getHeadContentType(option);
        assertEquals("application/json", head);
    }

    @Test
    public void testHeadCategoriesTodos() throws IOException {
        String option = "/categories/1/todos";
        String head = TodoInstance.getHeadContentType(option);
        assertEquals("application/json", head);
    }
}
