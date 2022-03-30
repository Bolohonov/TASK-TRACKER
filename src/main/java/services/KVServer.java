package services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Постман: https://www.getpostman.com/collections/a83b61d9e1c81c10575c
 */
public class KVServer {
    public static final int PORT = 8078;
    private final String API_KEY;
    private HttpServer server;
    private Map<String, String> data = new HashMap<>();

    public KVServer() throws IOException {
        API_KEY = generateApiKey();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/register", (h) -> {
            try {
                System.out.println("\n/register");
                switch (h.getRequestMethod()) {
                    case "GET":
                        sendText(h, API_KEY);
                        break;
                    default:
                        System.out.println("/register ждёт GET-запрос, а получил "
                                + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        });
        server.createContext("/save", (h) -> {
            try {
                System.out.println("\n/save");
                if (!hasAuth(h)) {
                    System.out.println("Запрос неавторизован, " +
                            "нужен параметр в query API_KEY со значением апи-ключа");
                    h.sendResponseHeaders(403, 0);
                    return;
                }
                switch (h.getRequestMethod()) {
                    case "POST":
                        String key = h.getRequestURI().getPath().substring("/save/".length());
                        if (key.isEmpty()) {
                            System.out.println("Key для сохранения пустой. key указывается " +
                                    "в пути: /save/{key}");
                            h.sendResponseHeaders(400, 0);
                            return;
                        }
                        String value = readText(h);
                        if (value.isEmpty()) {
                            System.out.println("Value для сохранения пустой. " +
                                    "value указывается в теле запроса");
                            h.sendResponseHeaders(400, 0);
                            return;
                        }
                        data.put(key, value);
                        System.out.println("Значение для ключа " + key + " успешно обновлено!");
                        h.sendResponseHeaders(200, 0);
                        break;
                    default:
                        System.out.println("/save ждёт POST-запрос, а получил: "
                                + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        });

        server.createContext("/load", (h) -> {
            try {
                System.out.println("\n/load");
                if (!hasAuth(h)) {
                    System.out.println("Запрос неавторизован, нужен параметр в query API_KEY " +
                            "со значением апи-ключа");
                    h.sendResponseHeaders(403, 0);
                    return;
                }
                switch (h.getRequestMethod()) {
                    case "GET":
                        String key = h.getRequestURI().getPath().substring("/load/".length());
                        if (key.isEmpty()) {
                            System.out.println("Key для загрузки пустой. " +
                                    "key указывается в пути: /load/{key}");
                            h.sendResponseHeaders(400, 0);
                            return;
                        }
                        if (!data.containsKey(key)
                                && !key.equals("getSingleTasks")
                                && !key.equals("getEpicTasks")
                                && !key.contains("getSubTasksByEpic=")) {
                            System.out.println("Не могу достать данные для ключа '" +
                                    key + "', " + "данные отсутствуют");
                            h.sendResponseHeaders(404, 0);
                            return;
                        }
                        if (key.equals("getSingleTasks")) {
                            String responseData = null;
                            for (String task : data.values()) {
                                JsonObject jo = JsonParser.parseString(task).getAsJsonObject();
                                if (jo.has("type")
                                        && jo.get("type").toString().contains("SINGLE")) {
                                    responseData = responseData + "\n" + task;
                                }
                            }
                            sendText(h, responseData);
                        }
                        if (key.equals("getEpicTasks")) {
                            String responseData = null;
                            for (String task : data.values()) {
                                JsonObject jo = JsonParser.parseString(task).getAsJsonObject();
                                if (jo.has("type")
                                        && jo.get("type").toString().contains("EPIC")) {
                                    responseData = responseData + "\n" + task;
                                }
                            }
                            sendText(h, responseData);
                        }
                        if (key.contains("getSubTasksByEpic=")) {
                            String id = key.split("=")[1];
                            String responseData = null;
                            String epic = data.get(id);
                            JsonObject jo = JsonParser.parseString(epic).getAsJsonObject();
                            if (jo.has("subTasksOfEpic")) {
                                JsonElement jsonElementSubTasks = jo.get("subTasksOfEpic");
                                responseData = jsonElementSubTasks.getAsString();
                            }
                            sendText(h, responseData);
                            System.out.println("Значение для ключа " +
                                    key + " успешно отправлено в ответ на запрос!");
                            return;
                        }
                        if (data.containsKey(key)) {
                            sendText(h, data.get(key));
                            System.out.println("Значение для ключа " +
                                    key + " успешно отправлено в ответ на запрос!");
                            return;
                        }
                        break;
                    default:
                        System.out.println("/load ждёт GET-запрос, а получил: " +
                                h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        });

        server.createContext("/delete", (h) -> {
            try {
                System.out.println("\n/delete");
                if (!hasAuth(h)) {
                    System.out.println("Запрос неавторизован, нужен параметр в query API_KEY " +
                            "со значением апи-ключа");
                    h.sendResponseHeaders(403, 0);
                    return;
                }
                switch (h.getRequestMethod()) {
                    case "DELETE":
                        String key = h.getRequestURI().getPath().substring("/delete/".length());
                        if (key.isEmpty()) {
                            System.out.println("Key для загрузки пустой. " +
                                    "key указывается в пути: /load/{key}");
                            h.sendResponseHeaders(400, 0);
                            return;
                        }
                        if (key.equals("ALL")) {
                            data.clear();
                            System.out.println("Значение для ключа DELETE " +
                                    key + " успешно отправлено в ответ на запрос!");
                            h.sendResponseHeaders(200, 0);
                        }
                        if (!data.containsKey(key) && !key.equals("ALL")) {
                            System.out.println("Не могу достать данные для ключа '" + key + "', " +
                                    "данные отсутствуют");
                            h.sendResponseHeaders(404, 0);
                            return;
                        }
                        data.remove(key);
                        System.out.println("Значение для ключа " + key + " успешно удалено " +
                                "в ответ на запрос!");
                        h.sendResponseHeaders(200, 0);
                        break;
                    default:
                        System.out.println("/delete ждёт DELETE-запрос, а получил: " +
                                h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        });
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        System.out.println("API_KEY: " + API_KEY);
        server.start();
    }

    private String generateApiKey() {
        return "" + System.currentTimeMillis();
    }

    protected boolean hasAuth(HttpExchange h) {
        String rawQuery = h.getRequestURI().getRawQuery();
        return rawQuery != null && (rawQuery.contains("API_KEY=" + API_KEY)
                || rawQuery.contains("API_KEY=DEBUG"));
    }

    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), "UTF-8");
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, 0);
        try (OutputStream os = h.getResponseBody()) {
            os.write(text.getBytes());
        }
    }

    public void stop(int delay) {
        server.stop(delay);
    }
}