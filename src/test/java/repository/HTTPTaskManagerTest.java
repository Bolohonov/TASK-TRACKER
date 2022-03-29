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
    void putSingleTaskStandardBehavior() throws ManagerSaveException, URISyntaxException,
            IntersectionException {
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
                "TestEpicDescription", 1051);
        manager.putTask(expectedEpicTask);
        Task actualEpicTask = manager.getTaskById(expectedEpicTask.getId());
        assertEquals(expectedEpicTask, actualEpicTask);
    }

    @Test
    void putSubTaskStandardBehavior() throws ManagerSaveException, URISyntaxException,
            IntersectionException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        EpicTask expectedEpicTask = new EpicTask("TestEpicName",
                "TestEpicDescription", 1041);
        manager.putTask(expectedEpicTask);
        SubTask expectedSubTask = new SubTask(expectedEpicTask, "TestNameSub1",
                "TestDescriptionSub1", 1044, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2022, 03, 10, 1, 00, 10)));
        manager.putTask(expectedSubTask);
        Task actualSubTask = manager.getTaskById(expectedSubTask.getId());
        assertEquals(expectedSubTask, actualSubTask);
    }

    @Test
    void updateSingleTaskStandardBehavior() throws ManagerSaveException, URISyntaxException,
            IntersectionException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        Task expectedTask = new SingleTask("TestSingleName",
                "TestSingleDescription", 1030, Optional.of(Duration.ofDays(2)),
                Optional.of(LocalDateTime
                        .of(2021, 06, 19, 7, 00, 10)));
        manager.putTask(expectedTask);
        expectedTask.setName("SingleName");
        expectedTask.setDescription("SingleDescription");
        expectedTask.setDuration(Duration.ofHours(10));
        expectedTask.setStartTime(LocalDateTime
                .of(2021, 01, 27, 7, 15, 10));
        manager.updateTask(expectedTask);
        Task actualTask = manager.getTaskById(expectedTask.getId());
        assertEquals(expectedTask, actualTask);
    }

    @Test
    void getSingleTaskStandardBehavior() throws ManagerSaveException, URISyntaxException,
            IntersectionException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        Task expectedTask = new SingleTask("TestSingleName",
                "TestSingleDescription", 1035, Optional.of(Duration.ofDays(2)),
                Optional.of(LocalDateTime
                        .of(2019, 06, 19, 7, 00, 10)));
        manager.putTask(expectedTask);
        Task actualTask = manager.getTaskById(expectedTask.getId());
        assertEquals(expectedTask, actualTask);
    }

    @Test
    void getEpicTaskStandardBehavior() throws ManagerSaveException, URISyntaxException,
            IntersectionException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        EpicTask expectedEpicTask = new EpicTask("TestEpicName",
                "TestEpicDescription", 1031);
        manager.putTask(expectedEpicTask);
        Task actualEpicTask = manager.getTaskById(expectedEpicTask.getId());
        assertEquals(expectedEpicTask, actualEpicTask);
    }

    @Test
    void getSubTaskStandardBehavior() throws ManagerSaveException, URISyntaxException,
            IntersectionException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        EpicTask expectedEpicTask = new EpicTask("TestEpicName",
                "TestEpicDescription", 1032);
        manager.putTask(expectedEpicTask);
        SubTask expectedSubTask = new SubTask(expectedEpicTask, "TestNameSub1",
                "TestDescriptionSub1", 1033, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2019, 03, 10, 1, 00, 10)));
        manager.putTask(expectedSubTask);
        Task actualSubTask = manager.getTaskById(expectedSubTask.getId());
        assertEquals(expectedSubTask, actualSubTask);
    }

    @Test
    public void removeAllTasks() throws ManagerSaveException, URISyntaxException, IntersectionException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        Repository<Task> testRep = new Repository();
        EpicTask epicTask1 = new EpicTask("TestEpicName",
                "TestEpicDescription", 1101);
        EpicTask epicTask2 = new EpicTask("TestEpicName2",
                "TestEpicDescription2", 1102);
        ;
        EpicTask epicTask3 = new EpicTask("TestEpicName3",
                "TestEpicDescription3", 1103);
        SubTask subTask1 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1104, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2018, 03, 12, 1, 00, 10)));
        SubTask subTask2 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1005, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2018, 03, 12, 3, 00, 10)));
        SubTask subTask3 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1006, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2018, 03, 12, 8, 00, 10)));
        SingleTask task1 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1007, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2018, 03, 13, 7, 00, 10)));
        SingleTask task2 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1008, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2018, 03, 13, 10, 00, 10)));
        testRep.putTask(epicTask1);
        testRep.putTask(epicTask2);
        testRep.putTask(epicTask3);
        manager.putTask(epicTask1);
        manager.putTask(epicTask2);
        manager.putTask(epicTask3);
        testRep.removeAllTasks();
        manager.removeAllTasks();
        assertEquals(testRep.getTasks(), manager.getEpicTasks());
        testRep.putTask(subTask1);
        testRep.putTask(subTask2);
        testRep.putTask(subTask3);
        manager.putTask(subTask1);
        manager.putTask(subTask2);
        manager.putTask(subTask3);
        testRep.removeAllTasks();
        manager.removeAllTasks();
        assertEquals(testRep.getTasks(), manager.getEpicTasks().get(1101).getSubTasks());
        testRep.putTask(task1);
        testRep.putTask(task2);
        manager.putTask(task1);
        manager.putTask(task2);
        testRep.removeAllTasks();
        manager.removeAllTasks();
        assertEquals(testRep.getTasks(), manager.getSingleTasks());
    }

    @Test
    void removeTaskById() throws ManagerSaveException, URISyntaxException, IntersectionException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        EpicTask epicTask1 = new EpicTask("TestEpicName",
                "TestEpicDescription", 1201);
        EpicTask epicTask2 = new EpicTask("TestEpicName2",
                "TestEpicDescription2", 1202);
        ;
        EpicTask epicTask3 = new EpicTask("TestEpicName3",
                "TestEpicDescription3", 1203);
        SubTask subTask1 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1204, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2017, 03, 12, 1, 00, 10)));
        SubTask subTask2 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1205, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2017, 03, 12, 3, 00, 10)));
        SubTask subTask3 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1206, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2017, 03, 12, 8, 00, 10)));
        SingleTask task1 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1207, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2017, 03, 13, 7, 00, 10)));
        SingleTask task2 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1208, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2017, 03, 13, 10, 00, 10)));
        manager.putTask(epicTask1);
        manager.putTask(epicTask2);
        manager.putTask(epicTask3);
        manager.removeTaskById(epicTask3.getId());
        assertFalse(manager.getEpicTasks().containsKey(epicTask3.getId()));
        manager.putTask(subTask1);
        manager.putTask(subTask2);
        manager.putTask(subTask3);
        manager.removeTaskById(subTask1.getId());
        assertFalse(manager.getSubTasksByEpic(epicTask1).containsKey(subTask1.getId()));
        manager.putTask(task1);
        manager.putTask(task2);
        manager.removeTaskById(task2.getId());
        assertFalse(manager.getSingleTasks().containsKey(task2.getId()));
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