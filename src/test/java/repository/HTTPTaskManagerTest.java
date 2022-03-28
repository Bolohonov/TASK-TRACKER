package repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.HttpTaskServer;
import services.KVServer;
import tasks.SingleTask;
import tasks.Task;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HTTPTaskManagerTest {

    private final HttpTaskServer httpTaskServer = new HttpTaskServer();
    private final KVServer kvServer = new KVServer();
    Managers managers;
    TaskManager manager = managers.getDefault();

    HTTPTaskManagerTest() throws IOException {
    }


    @BeforeEach
    void run() throws IOException, ManagerSaveException {
        httpTaskServer.run();
        kvServer.start();
    }

    @AfterEach
    void stop() {
        httpTaskServer.stop();
        kvServer.stop();
    }

    @Test
    void putTask() throws IntersectionException, ManagerSaveException {
        Task task = new SingleTask("TestSingleName",
                "TestSingleDescription", 1023, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2021, 06, 19, 7, 00, 10)));
        manager.putTask(task);
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