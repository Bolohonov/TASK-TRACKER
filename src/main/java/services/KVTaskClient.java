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

//    public void save(String json) {
//        //POST /save/<ключ>?API_KEY=
//        JsonElement jsonElement = JsonParser
//                .parseString(body);
//        if(!jsonElement.isJsonObject()) { // проверяем, точно ли мы получили JSON-объект
//            System.out.println("Ответ от сервера не соответствует ожидаемому.");
//            httpExchange.sendResponseHeaders(400, 0);
//            return;
//        }
//        // преобразуем результат разбора текста в JSON-объект
//        JsonObject jsonObject = jsonElement.getAsJsonObject();
//        Gson gson = new Gson();
//        Task task = gson.fromJson(jsonObject, Task.class);
//        Gson gson = new Gson();
//        gson.
//        String key;
//        String value;
//        HttpRequest requestToSave = HttpRequest
//                .newBuilder()
//                .uri(URI.create(url.toString() + "/save"))
//                .POST()
//                .build();
//    }

    public String load() {
        //GET /load/<ключ>?API_KEY=
        return new String();
    }
}
