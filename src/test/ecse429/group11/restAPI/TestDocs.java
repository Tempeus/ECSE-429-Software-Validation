package ecse429.group11.restAPI;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestDocs {

    @Before
    public void startInstance(){
        TodoInstance.runApplication();
    }

    @Test
    public void testGetDocsStatusCode() throws IOException {
        assertEquals(TodoInstance.getStatusCode("/docs"), TodoInstance.SC_SUCCESS);
    }
}
