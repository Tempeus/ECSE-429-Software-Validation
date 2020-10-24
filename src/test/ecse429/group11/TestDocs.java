package ecse429.group11;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestDocs {

    @Test
    public void testGetDocsStatusCode() throws IOException {
        assertEquals(TodoInstance.getStatusCode("/docs"), TodoInstance.SC_SUCCESS);
    }
}
