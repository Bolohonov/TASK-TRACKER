package services;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ManagerSaveException;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {

    private final HttpTaskServer httpTaskServer = new HttpTaskServer();
    private final KVServer kvServer = new KVServer();


    HttpTaskServerTest() throws IOException {
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
    public void shouldPutTaskByPostRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8077/tasks/task/");
        Gson gson = new Gson();
        Task task = new SingleTask("TestSingleName",
                "TestSingleDescription", 1023, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2021, 06, 19, 7, 00, 10)));
        String json = gson.toJson(task);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int expectedStatusCode = 200;
        assertEquals(expectedStatusCode, response.statusCode());
    }

    @Test
    public void shouldPutSubTaskTaskByPostRequest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8077/tasks/epic/");
        Gson gson = new Gson();
        EpicTask epic = new EpicTask("TestEpicName",
                "TestEpicDescription", 1022);
        String json = gson.toJson(epic);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        SubTask subTask = new SubTask(epic, "TestNameSub1",
                "TestDescriptionSub1", 1026, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2020, 03, 10, 8, 00, 10)));
        json = gson.toJson(subTask);
        final HttpRequest.BodyPublisher body2 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request2 = HttpRequest.newBuilder().uri(url).POST(body2).build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        System.out.println(response2.body());
        int expectedStatusCode = 200;
        assertEquals(expectedStatusCode, response.statusCode());
    }
}