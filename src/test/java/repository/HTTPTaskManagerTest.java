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


    @BeforeAll
    static void run() throws IOException, ManagerSaveException {
        new KVServer().start();
        new HttpTaskServer().run();
    }

    @Test
    void putTask() throws IntersectionException, ManagerSaveException, URISyntaxException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        Task task = new SingleTask("TestSingleName",
                "TestSingleDescription", 1023, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2021, 06, 19, 7, 00, 10)));
        manager.putTask(task);
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