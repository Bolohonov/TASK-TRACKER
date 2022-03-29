package services;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    private final HttpClient client =
            HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
    private final URI url;
    private String API_KEY;

    public KVTaskClient(URI url) {
        this.url = url;
        this.API_KEY = register(url);
    }

    public String register(URI url) {
        String API_KEY = null;
        URI uri = URI.create(url.toString() + "/register");
        HttpRequest requestRegistration = HttpRequest
                .newBuilder()
                .uri(uri)
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(requestRegistration,
                    HttpResponse.BodyHandlers.ofString());
            API_KEY = response.body();
            System.out.println("Код статуса: " + response.statusCode());
            System.out.println("Ответ: " + response.body());
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса ресурса по URL-адресу: '"
                    + URI.create(url.toString() + "/register") + "' возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        return API_KEY;
    }

    public void put(String key, String json) {
        URI uri = URI.create(url.toString() + "/save" + "/" + key + "?API_KEY=" + API_KEY);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest requestPost = HttpRequest
                .newBuilder()
                .uri(uri)
                .POST(body)
                .header("content-type", "application/json")
                .build();
        try {
            client.send(requestPost, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            System.out.println("Во время выполнения запроса по адресу:"
                    + uri + " произошла ошибка\n"
                    + e.getMessage() + "\n" + e.getStackTrace());
        }

    }

    public String load(String key) {
        HttpResponse<String> response = null;
        URI uri = URI.create(url.toString() + "/load" + "/" + key + "?API_KEY=" + API_KEY);
        HttpRequest requestGet = HttpRequest
                .newBuilder()
                .uri(uri)
                .GET()
                .header("content-type", "application/json")
                .build();
        try {
            response = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса ресурса по URL-адресу: '"
                    + uri + "' возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        return response.body();
    }

    public void delete(String key, String json) {
        switch(key) {
            case "removeAllTasks":
            URI uri =
                    URI.create(url.toString() + "/save" + "/removeAllTasks"
                            + "?API_KEY=" + API_KEY);
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(uri)
                    .header("content-type", "application/json")
                    .build();
            try {
                HttpResponse<String> response = client.
                        send(request, HttpResponse.BodyHandlers.ofString());
            } catch (InterruptedException | IOException e) {
                System.out.println("Во время выполнения запроса по адресу:"
                        + uri + " произошла ошибка\n"
                        + e.getMessage() + "\n" + e.getStackTrace());
            }
            break;
            case "removeTaskById=":
                uri = URI.create(url.toString() + "/save" + "/?id=" + key + "&API_KEY=" + API_KEY);
                request = HttpRequest
                        .newBuilder()
                        .uri(uri)
                        .DELETE()
                        .header("content-type", "application/json")
                        .build();
                try {
                    HttpResponse<String> response = client.
                            send(request, HttpResponse.BodyHandlers.ofString());
                } catch (InterruptedException | IOException e) {
                    System.out.println("Во время выполнения запроса по адресу:"
                            + uri + " произошла ошибка\n"
                            + e.getMessage() + "\n" + e.getStackTrace());
                }
            break;
        }
    }
}
