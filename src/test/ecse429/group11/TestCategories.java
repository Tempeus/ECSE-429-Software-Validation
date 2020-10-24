package ecse429.group11;

import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestCategories {

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
    public void testGetCategoriesWithValidID(){

    }

    @Test
    public void testGetCategoriesWithInvalidID(){

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

    //DELETE categories/id
}
