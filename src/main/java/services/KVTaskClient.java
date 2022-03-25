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

    HttpClient client = HttpClient.newHttpClient();
    URI url;
    String API_KEY;

    public KVTaskClient(URI url) {
        this.url = url;
        API_KEY = register(url);
    }

    public String register(URI url) {
        String API_KEY = null;
        HttpRequest requestRegistration = HttpRequest
                .newBuilder()
                .uri(URI.create(url.toString() + "/register"))
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
        //POST /save/<ключ>?API_KEY=
        URI uri = URI.create(url.toString() + "/save" + "?API_KEY=" + API_KEY);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(uri)
                .POST(body)
                .build();
        try {
            HttpResponse<String> response = client.
                    send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            System.out.println("Во время выполнения запроса по адресу:"
                    + uri + " произошла ошибка\n"
                    + e.getMessage() + "\n" + e.getStackTrace());
        }

    }

    public String load(String key) {
        HttpResponse<String> response = null;
        URI uri = URI.create(url.toString() + "/load" + "/?id=" + key + "&API_KEY=" + API_KEY);
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(url)
                .GET()
                .header("Accept", "application/json")
                .build();
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
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
            URI uri = URI.create(url.toString() + "/save" + "?API_KEY=" + API_KEY);
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(uri)
                    .DELETE()
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
