package ecse429.group11;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ConnectException;

import static org.junit.Assert.assertEquals;

public class TestShutdown {

    @Before
    public void startInstance(){
        TodoInstance.runApplication();
    }

    @Test
    public void testShutdownServer(){
        String shutdown_url = "/shutdown";
        boolean server_status = true;
        try {
            TodoInstance.getStatusCode(shutdown_url);
        } catch (ConnectException ce){
            server_status = false;
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(false, server_status);
    }
}
