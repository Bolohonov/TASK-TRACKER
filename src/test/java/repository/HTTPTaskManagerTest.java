package repository;

import org.junit.jupiter.api.*;
import services.HttpTaskServer;
import services.KVServer;
import tasks.SingleTask;
import tasks.Task;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HTTPTaskManagerTest {

//    KVServer kvServer = new KVServer();
//    HttpTaskServer httpTaskServer = new HttpTaskServer();
//    Managers managers = new Managers();
//    TaskManager manager = managers.getDefault();

    HTTPTaskManagerTest() throws IOException, ManagerSaveException, URISyntaxException {
    }


//    @BeforeAll
//    static void run() throws IOException, ManagerSaveException {
//        new KVServer().start();
//        new HttpTaskServer().run();
//    }

    @Test
    void putTask() throws IntersectionException, ManagerSaveException, URISyntaxException, IOException {
        KVServer kvServer = new KVServer();
        kvServer.start();
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        httpTaskServer.run();
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        Task task = new SingleTask("TestSingleName",
                "TestSingleDescription", 1023, Optional.of(Duration.ofDays(2)),
                Optional.of(LocalDateTime
                        .of(2021, 06, 19, 7, 00, 10)));
        System.out.println(task.getDuration());
        Task task2 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1028, Optional.of(Duration.ofSeconds(55)),
                Optional.of(LocalDateTime
                        .of(2021, 06, 19, 7, 00, 10)));
        System.out.println(task2.getDuration());
        manager.putTask(task);
        System.out.println(kvServer.getData().get(task.getId()));
        //kvServer.getData().entrySet().forEach(System.out::println);
        SingleTask taskFrom = (SingleTask) manager.getTaskById(task.getId());
        System.out.println(taskFrom.toString());
        assertEquals(task, manager.getTaskById(task.getId()));
    }

    @Test
    void updateTask() {
    }

    @Test
    void getTaskById() {
    }

    @Test
    void removeAllTasks() {
    }

    @Test
    void removeTaskById() {
    }

    @Test
    void getSingleTasks() {
    }

    @Test
    void getEpicTasks() {
    }

    @Test
    void getSubTasksByEpic() {
    }

    @Test
    void getHistory() {
    }
}