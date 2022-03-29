package repository;

import org.junit.jupiter.api.*;
import services.HttpTaskServer;
import services.KVServer;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
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

//    HTTPTaskManagerTest() throws IOException, ManagerSaveException, URISyntaxException {
//    }


    @BeforeAll
    static void run() throws IOException, ManagerSaveException {
        KVServer kvServer = new KVServer();
        kvServer.start();
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        httpTaskServer.run();
    }

    @Test
    void putSingleTaskStandardBehavior() throws ManagerSaveException, URISyntaxException, IntersectionException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        Task expectedTask = new SingleTask("TestSingleName",
                "TestSingleDescription", 1023, Optional.of(Duration.ofDays(2)),
                Optional.of(LocalDateTime
                        .of(2021, 06, 19, 7, 00, 10)));
        manager.putTask(expectedTask);
        Task actualTask = manager.getTaskById(expectedTask.getId());
        assertEquals(expectedTask, actualTask);
    }

    @Test
    void putEpicTaskStandardBehavior() throws ManagerSaveException, URISyntaxException, IntersectionException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        EpicTask expectedEpicTask = new EpicTask("TestEpicName",
                "TestEpicDescription", 1001);
        manager.putTask(expectedEpicTask);
        Task actualEpicTask = manager.getTaskById(expectedEpicTask.getId());
        assertEquals(expectedEpicTask, actualEpicTask);
    }

    @Test
    void putSubTaskStandardBehavior() throws ManagerSaveException, URISyntaxException, IntersectionException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        EpicTask expectedEpicTask = new EpicTask("TestEpicName",
                "TestEpicDescription", 1001);
        manager.putTask(expectedEpicTask);
        SubTask expectedSubTask = new SubTask(expectedEpicTask, "TestNameSub1",
                "TestDescriptionSub1", 1004, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2022, 03, 10, 1, 00, 10)));
        manager.putTask(expectedSubTask);
        Task actualSubTask = manager.getTaskById(expectedSubTask.getId());
        assertEquals(expectedSubTask, actualSubTask);
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