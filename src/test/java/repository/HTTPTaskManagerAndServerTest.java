package repository;

import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import services.ConfigTaskJsonAdapter;
import services.HttpTaskServer;
import services.KVServer;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HTTPTaskManagerAndServerTest {

    @BeforeAll
    static void run() throws IOException {
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
        manager.removeAllTasks();
        Task expectedTask = new SingleTask("TestSingleName",
                "TestSingleDescription", 1023, Optional.of(Duration.ofDays(2)),
                Optional.of(LocalDateTime
                        .of(2021, 06, 19, 7, 00, 10)));
        manager.putTask(expectedTask);
        Task actualTask = manager.getTaskById(expectedTask.getId());
        assertEquals(expectedTask, actualTask);
    }

    @Test
    void putEpicTaskWithoutSubTasksStandardBehavior() throws ManagerSaveException,
            URISyntaxException,
            IntersectionException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        manager.removeAllTasks();
        EpicTask expectedEpicTask = new EpicTask("TestEpicName",
                "TestEpicDescription", 1051);
        manager.putTask(expectedEpicTask);
        Task actualEpicTask = manager.getTaskById(expectedEpicTask.getId());
        assertEquals(expectedEpicTask, actualEpicTask);
    }

    @Test
    void putEpicTaskWithSubTasksStandardBehavior() throws ManagerSaveException,
            URISyntaxException,
            IntersectionException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        EpicTask expectedEpicTask = new EpicTask("TestEpicName",
                "TestEpicDescription", 1071);
        SubTask expectedSubTask1 = new SubTask(expectedEpicTask.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1073, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2016, 03, 12, 1, 00, 10)));
        SubTask expectedSubTask2 = new SubTask(expectedEpicTask.getId(), "TestNameSub2",
                "TestDescriptionSub2", 1074, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2016, 03, 11, 3, 00, 10)));
        SubTask expectedSubTask3 = new SubTask(expectedEpicTask.getId(), "TestNameSub3",
                "TestDescriptionSub3", 1075, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2016, 03, 12, 5, 00, 10)));
        manager.putTask(expectedEpicTask);
        manager.putTask(expectedSubTask1);
        manager.putTask(expectedSubTask2);
        manager.putTask(expectedSubTask3);
        expectedEpicTask.addSubTask(expectedSubTask1);
        expectedEpicTask.addSubTask(expectedSubTask2);
        expectedEpicTask.addSubTask(expectedSubTask3);
        Task actualEpicTask = manager.getTaskById(expectedEpicTask.getId());
        assertEquals(expectedEpicTask, actualEpicTask);
    }

    @Test
    void putSubTaskStandardBehavior() throws ManagerSaveException, URISyntaxException,
            IntersectionException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        manager.removeAllTasks();
        EpicTask expectedEpicTask = new EpicTask("TestEpicName",
                "TestEpicDescription", 1041);
        manager.putTask(expectedEpicTask);
        SubTask expectedSubTask = new SubTask(expectedEpicTask.getId(), "TestNameSub1",
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
        manager.removeAllTasks();
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
        manager.removeAllTasks();
        Task expectedTask = new SingleTask("TestSingleName",
                "TestSingleDescription", 1035, Optional.of(Duration.ofDays(2)),
                Optional.of(LocalDateTime
                        .of(2019, 06, 19, 7, 00, 10)));
        manager.putTask(expectedTask);
        Task actualTask = manager.getTaskById(expectedTask.getId());
        assertEquals(expectedTask, actualTask);
    }

    @Test
    void getSingleTaskWrongId() throws ManagerSaveException,
            URISyntaxException, IntersectionException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        manager.removeAllTasks();
        Task expectedTask = new SingleTask("TestSingleName",
                "TestSingleDescription", 1035, Optional.of(Duration.ofDays(2)),
                Optional.of(LocalDateTime
                        .of(2019, 06, 19, 7, 00, 10)));
        manager.putTask(expectedTask);
        assertEquals(null , manager.getTaskById(1034));
    }

    @Test
    void getEpicTaskStandardBehavior() throws ManagerSaveException, URISyntaxException,
            IntersectionException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        manager.removeAllTasks();
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
        manager.removeAllTasks();
        EpicTask expectedEpicTask = new EpicTask("TestEpicName",
                "TestEpicDescription", 1032);
        manager.putTask(expectedEpicTask);
        SubTask expectedSubTask = new SubTask(expectedEpicTask.getId(), "TestNameSub1",
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
        manager.removeAllTasks();
        Repository<Task> testRep = new Repository();
        EpicTask epicTask1 = new EpicTask("TestEpicName",
                "TestEpicDescription", 1101);
        EpicTask epicTask2 = new EpicTask("TestEpicName2",
                "TestEpicDescription2", 1102);
        EpicTask epicTask3 = new EpicTask("TestEpicName3",
                "TestEpicDescription3", 1103);
        SubTask subTask1 = new SubTask(epicTask1.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1104, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2018, 03, 12, 1, 00, 10)));
        SubTask subTask2 = new SubTask(epicTask1.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1005, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2018, 03, 12, 3, 00, 10)));
        SubTask subTask3 = new SubTask(epicTask1.getId(), "TestNameSub1",
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
        testRep.putTask(epicTask1);
        testRep.putTask(epicTask2);
        testRep.putTask(epicTask3);
        manager.putTask(epicTask1);
        manager.putTask(epicTask2);
        manager.putTask(epicTask3);
        testRep.putTask(subTask1);
        testRep.putTask(subTask2);
        testRep.putTask(subTask3);
        manager.putTask(subTask1);
        manager.putTask(subTask2);
        manager.putTask(subTask3);
        testRep.removeAllTasks();
        manager.removeAllTasks();
        assertEquals(testRep.getTasks(), manager.getEpicTasks());
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
        manager.removeAllTasks();
        EpicTask epicTask1 = new EpicTask("TestEpicName",
                "TestEpicDescription", 1201);
        EpicTask epicTask2 = new EpicTask("TestEpicName2",
                "TestEpicDescription2", 1202);
        EpicTask epicTask3 = new EpicTask("TestEpicName3",
                "TestEpicDescription3", 1203);
        SubTask subTask1 = new SubTask(epicTask1.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1204, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2017, 03, 12, 1, 00, 10)));
        SubTask subTask2 = new SubTask(epicTask1.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1205, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2017, 03, 12, 3, 00, 10)));
        SubTask subTask3 = new SubTask(epicTask1.getId(), "TestNameSub1",
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
    void getSingleTasks() throws ManagerSaveException, URISyntaxException, IntersectionException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        manager.removeAllTasks();
        Map<Integer, SingleTask> testRep = new HashMap<>();
        SingleTask task1 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1007, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2018, 9, 13, 7, 00, 10)));
        SingleTask task2 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1008, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2018, 9, 13, 10, 00, 10)));
        SingleTask task3 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1009, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2018, 9, 14, 10, 00, 10)));
        testRep.put(1007, task1);
        testRep.put(1008, task2);
        testRep.put(1009, task3);
        manager.putTask(task1);
        manager.putTask(task2);
        manager.putTask(task3);
        assertEquals(testRep, manager.getSingleTasks());
    }

    @Test
    void getEpicTasks() throws ManagerSaveException, URISyntaxException, IntersectionException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        Map<Integer, EpicTask> testRep = new HashMap<>();
        manager.removeAllTasks();
        EpicTask task1 = new EpicTask("TestEpicName",
                "TestEpicDescription", 1311);
        EpicTask task2 = new EpicTask("TestEpicName2",
                "TestEpicDescription2", 1312);
        EpicTask task3 = new EpicTask("TestEpicName3",
                "TestEpicDescription3", 1313);
        SubTask subTask1 = new SubTask(task1.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1314, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2015, 05, 12, 1, 00, 10)));
        SubTask subTask2 = new SubTask(task1.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1315, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2015, 05, 12, 3, 00, 10)));
        SubTask subTask3 = new SubTask(task1.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1316, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2015, 05, 12, 8, 00, 10)));
        SubTask subTask4 = new SubTask(task2.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1317, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2015, 05, 13, 8, 00, 10)));
        task1.addSubTask(subTask1);
        task1.addSubTask(subTask2);
        task1.addSubTask(subTask3);
        task2.addSubTask(subTask4);
        testRep.put(task1.getId(), task1);
        testRep.put(task2.getId(), task2);
        testRep.put(task3.getId(), task3);
        manager.putTask(task1);
        manager.putTask(task2);
        manager.putTask(task3);
        manager.putTask(subTask1);
        manager.putTask(subTask2);
        manager.putTask(subTask3);
        manager.putTask(subTask4);
        assertEquals(testRep, manager.getEpicTasks());
    }

    @Test
    void getSubTasksByEpic() throws ManagerSaveException, URISyntaxException, IntersectionException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        Map<Integer, SubTask> testRep = new HashMap<>();
        manager.removeAllTasks();
        EpicTask task1 = new EpicTask("TestEpicName",
                "TestEpicDescription", 1211);
        EpicTask task2 = new EpicTask("TestEpicName2",
                "TestEpicDescription2", 1212);
        EpicTask task3 = new EpicTask("TestEpicName3",
                "TestEpicDescription3", 1213);
        SubTask subTask1 = new SubTask(task1.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1214, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2017, 05, 12, 1, 00, 10)));
        SubTask subTask2 = new SubTask(task1.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1215, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2017, 05, 12, 3, 00, 10)));
        SubTask subTask3 = new SubTask(task1.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1216, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2017, 05, 12, 8, 00, 10)));
        SubTask subTask4 = new SubTask(task2.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1217, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2017, 05, 13, 8, 00, 10)));
        task1.addSubTask(subTask1);
        task1.addSubTask(subTask2);
        task1.addSubTask(subTask3);
        task2.addSubTask(subTask4);
        testRep.put(subTask1.getId(), subTask1);
        testRep.put(subTask2.getId(), subTask2);
        testRep.put(subTask3.getId(), subTask3);
        manager.putTask(task1);
        manager.putTask(task2);
        manager.putTask(task3);
        manager.putTask(subTask1);
        manager.putTask(subTask2);
        manager.putTask(subTask3);
        manager.putTask(subTask4);
        assertEquals(testRep, manager.getSubTasksByEpic(task1));
        testRep.clear();
        testRep.put(subTask4.getId(), subTask4);
        assertEquals(testRep, manager.getSubTasksByEpic(task2));
        testRep.clear();
        assertEquals(testRep, manager.getSubTasksByEpic(task3));
    }

    @Test
    void getHistory() throws IntersectionException, ManagerSaveException, URISyntaxException {
        Managers managers = new Managers();
        TaskManager manager = managers.getDefault();
        List<Task> list = new LinkedList<>();
        manager.removeAllTasks();
        EpicTask task1 = new EpicTask("TestEpicName",
                "TestEpicDescription", 1211);
        EpicTask task2 = new EpicTask("TestEpicName2",
                "TestEpicDescription2", 1212);
        EpicTask task3 = new EpicTask("TestEpicName3",
                "TestEpicDescription3", 1213);
        SubTask subTask1 = new SubTask(task1.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1214, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2017, 05, 12, 1, 00, 10)));
        SubTask subTask2 = new SubTask(task1.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1215, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2017, 05, 12, 3, 00, 10)));
        SubTask subTask3 = new SubTask(task1.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1216, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2017, 05, 12, 8, 00, 10)));
        SubTask subTask4 = new SubTask(task2.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1217, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2017, 05, 13, 8, 00, 10)));
        task1.addSubTask(subTask1);
        task1.addSubTask(subTask2);
        task1.addSubTask(subTask3);
        task2.addSubTask(subTask4);
        manager.getHistory().clear();
        manager.putTask(task1);
        manager.putTask(task2);
        manager.putTask(task3);
        manager.getHistory().clear();
        manager.putTask(subTask1);
        manager.putTask(subTask2);
        manager.putTask(subTask3);
        list.add(task1);
        manager.putTask(subTask4);
        list.add(task2);
        manager.getTaskById(task1.getId());
        list.remove(task1);
        list.add(task1);
        manager.getTaskById(subTask2.getId());
        list.add(subTask2);
        manager.getTaskById(subTask4.getId());
        list.add(subTask4);
        manager.getTaskById(task1.getId());
        list.add(subTask1);
        list.remove(subTask2);
        list.add(subTask2);
        list.add(subTask3);
        list.remove(task1);
        list.add(task1);
        manager.removeTaskById(task2.getId());
        list.remove(task2);
        list.remove(subTask4);
        List<Task> actualList = new LinkedList<>();
        int length = manager.getHistory().size();
        actualList.add(manager.getHistory().get(length - 4));
        actualList.add(manager.getHistory().get(length - 3));
        actualList.add(manager.getHistory().get(length - 2));
        actualList.add(manager.getHistory().get(length - 1));
        assertEquals(list, actualList);
    }

    @Test
    public void shouldHttpTaskServerPutAndGetTask() throws IOException,
            InterruptedException, URISyntaxException {

        HTTPTaskManager manager = new HTTPTaskManager(Paths.get("/localhost"));
        manager.removeAllTasks();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8077/tasks/task/");
        Gson gson = ConfigTaskJsonAdapter.getGsonBuilder().create();
        Task expectedTask = new SingleTask("TestSingleName",
                "TestSingleDescription", 1023, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2021, 06, 19, 7, 00, 10)));
        String json = gson.toJson(expectedTask);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest requestPost = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(requestPost,
                HttpResponse.BodyHandlers.ofString());
        int expectedStatusCode = 200;
        assertEquals(expectedStatusCode, response.statusCode());

        URI urlGet = URI.create("http://localhost:8077/tasks/task/?id=1023");
        HttpRequest requestGet = HttpRequest.newBuilder().uri(urlGet).GET().build();
        HttpResponse<String> responseGet = client.send(requestGet,
                HttpResponse.BodyHandlers.ofString());
        String jsonFromKV = responseGet.body();
        assertEquals(expectedTask.toString(), jsonFromKV.toString());
    }

    @Test
    public void shouldHttpTaskServerPutAndGetEpicTask() throws IOException,
            InterruptedException, URISyntaxException {

        HTTPTaskManager manager = new HTTPTaskManager(Paths.get("/localhost"));
        manager.removeAllTasks();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8077/tasks/task/");
        Gson gson = ConfigTaskJsonAdapter.getGsonBuilder().create();
        EpicTask epic = new EpicTask("TestEpicName",
                "TestEpicDescription", 1022);
        String json = gson.toJson(epic);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest requestPost = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(requestPost,
                HttpResponse.BodyHandlers.ofString());
        int expectedStatusCode = 200;
        assertEquals(expectedStatusCode, response.statusCode());

        URI urlGet = URI.create("http://localhost:8077/tasks/task/?id=1022");
        HttpRequest requestGet = HttpRequest.newBuilder().uri(urlGet).GET().build();
        HttpResponse<String> responseGet = client.send(requestGet,
                HttpResponse.BodyHandlers.ofString());
        String jsonFromKV = responseGet.body();
        assertEquals(epic.toString(), jsonFromKV);
    }

    @Test
    public void shouldHttpTaskServerPutAndGetEpicTaskWithSubTasks() throws IOException,
            InterruptedException, URISyntaxException {

        HTTPTaskManager manager = new HTTPTaskManager(Paths.get("/localhost"));
        manager.removeAllTasks();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8077/tasks/task/");
        Gson gson = ConfigTaskJsonAdapter.getGsonBuilder().create();
        EpicTask epic = new EpicTask("TestEpicName",
                "TestEpicDescription", 1025);
        String json = gson.toJson(epic);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest requestPost = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(requestPost,
                HttpResponse.BodyHandlers.ofString());
        int expectedStatusCode = 200;
        assertEquals(expectedStatusCode, response.statusCode());


        SubTask subTask1 = new SubTask(epic.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1026, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2020, 03, 10, 8, 00, 10)));

        json = gson.toJson(subTask1);
        final HttpRequest.BodyPublisher bodySubTask1 = HttpRequest.BodyPublishers.ofString(json);
        requestPost = HttpRequest.newBuilder().uri(url).POST(bodySubTask1).build();
        response = client.send(requestPost, HttpResponse.BodyHandlers.ofString());
        assertEquals(expectedStatusCode, response.statusCode());

        SubTask subTask2 = new SubTask(epic.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1027, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2020, 03, 11, 5, 00, 10)));

        json = gson.toJson(subTask2);
        final HttpRequest.BodyPublisher bodySubTask2 = HttpRequest.BodyPublishers.ofString(json);
        requestPost = HttpRequest.newBuilder().uri(url).POST(bodySubTask2).build();
        response = client.send(requestPost, HttpResponse.BodyHandlers.ofString());
        assertEquals(expectedStatusCode, response.statusCode());

        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);

        URI urlGet = URI.create("http://localhost:8077/tasks/task/?id=1025");
        HttpRequest requestGet = HttpRequest.newBuilder().uri(urlGet).GET().build();
        HttpResponse<String> responseGet = client.send(requestGet,
                HttpResponse.BodyHandlers.ofString());
        String jsonFromKV = responseGet.body();
        assertEquals(epic.toString(), jsonFromKV);
    }

    @Test
    public void shouldHttpTaskServerPutAndRemoveEpicTaskWithSubTasks() throws IOException,
            InterruptedException, URISyntaxException {

        HTTPTaskManager manager = new HTTPTaskManager(Paths.get("/localhost"));
        manager.removeAllTasks();
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8077/tasks/task/");
        Gson gson = ConfigTaskJsonAdapter.getGsonBuilder().create();
        EpicTask epic = new EpicTask("TestEpicName",
                "TestEpicDescription", 1025);
        String json = gson.toJson(epic);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest requestPost = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(requestPost,
                HttpResponse.BodyHandlers.ofString());
        int expectedStatusCode = 200;
        assertEquals(expectedStatusCode, response.statusCode());


        SubTask subTask1 = new SubTask(epic.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1026, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2020, 03, 10, 8, 00, 10)));

        json = gson.toJson(subTask1);
        final HttpRequest.BodyPublisher bodySubTask1 = HttpRequest.BodyPublishers.ofString(json);
        requestPost = HttpRequest.newBuilder().uri(url).POST(bodySubTask1).build();
        response = client.send(requestPost, HttpResponse.BodyHandlers.ofString());
        assertEquals(expectedStatusCode, response.statusCode());

        SubTask subTask2 = new SubTask(epic.getId(), "TestNameSub1",
                "TestDescriptionSub1", 1027, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2020, 03, 11, 5, 00, 10)));

        json = gson.toJson(subTask2);
        final HttpRequest.BodyPublisher bodySubTask2 = HttpRequest.BodyPublishers.ofString(json);
        requestPost = HttpRequest.newBuilder().uri(url).POST(bodySubTask2).build();
        response = client.send(requestPost, HttpResponse.BodyHandlers.ofString());
        assertEquals(expectedStatusCode, response.statusCode());

        epic.addSubTask(subTask1);
        epic.addSubTask(subTask2);

        URI urlGet = URI.create("http://localhost:8077/tasks/task/?id=1026");
        HttpRequest requestGet = HttpRequest.newBuilder().uri(urlGet).GET().build();
        HttpResponse<String> responseGet = client.send(requestGet,
                HttpResponse.BodyHandlers.ofString());
        assertEquals(expectedStatusCode, responseGet.statusCode());

        urlGet = URI.create("http://localhost:8077/tasks/task/?id=1027");
        requestGet = HttpRequest.newBuilder().uri(urlGet).GET().build();
        responseGet = client.send(requestGet,
                HttpResponse.BodyHandlers.ofString());
        assertEquals(expectedStatusCode, responseGet.statusCode());

        URI urlDelete = URI.create("http://localhost:8077/tasks/task/?id=1025");
        HttpRequest requestDelete = HttpRequest.newBuilder().uri(urlDelete).DELETE().build();
        HttpResponse<String> responseDelete = client.send(requestDelete,
                HttpResponse.BodyHandlers.ofString());
        assertEquals(expectedStatusCode, responseDelete.statusCode());


        urlGet = URI.create("http://localhost:8077/tasks/task/?id=1026");
        requestGet = HttpRequest.newBuilder().uri(urlGet).GET().build();
        responseGet = client.send(requestGet,
                HttpResponse.BodyHandlers.ofString());
        int newExpectedCode = 404;
        assertEquals(newExpectedCode, responseGet.statusCode());

        urlGet = URI.create("http://localhost:8077/tasks/task/?id=1027");
        requestGet = HttpRequest.newBuilder().uri(urlGet).GET().build();
        responseGet = client.send(requestGet,
                HttpResponse.BodyHandlers.ofString());
        assertEquals(newExpectedCode, responseGet.statusCode());
    }
}