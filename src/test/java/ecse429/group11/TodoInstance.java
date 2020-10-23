package ecse429.group11;

import java.io.IOException;

public class TodoInstance {

    public static void runApplication(){
        try {
            Process process = Runtime.getRuntime().exec("java -jar runTodoManagerRestAPI-1.5.5.jar");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void killInstance(){
        try {
            TestTodos.send("GET","/shutdown");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
