package services;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import repository.IntersectionException;
import repository.ManagerSaveException;
import repository.Managers;
import repository.TaskManager;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;

public class HttpTaskServer {

    private static final int PORT = 8079;
    HttpServer httpServer = HttpServer.create();

    public HttpTaskServer() throws IOException {
    }

    private static Managers managers;

    static {
        try {
            managers = new Managers();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        }
    }

    private static final TaskManager manager = managers.getTaskManagerToFile();

    public void run() throws IOException, ManagerSaveException {
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler());
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        Gson gson = new Gson();
        String task = gson.toJson(manager.getTaskById(1007));
        System.out.println(task);
        JsonElement jsonElement = JsonParser
                .parseString(task);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement jsonElementDurationValue = jsonObject.get("duration");
        JsonElement jsonElementDuration = JsonParser
                .parseString(jsonElementDurationValue.toString());
        System.out.println(jsonElementDuration.toString());
        //httpServer.stop(1);
    }

    static class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "Ответ на запрос: ";
            String method = httpExchange.getRequestMethod();

            switch(method) {
                case "GET":
                    switch(getPathFromGetRequest(httpExchange)) {
                        case "getSingleTasks":
                            for (SingleTask task : manager.getSingleTasks().values()) {
                                response = response + "\n" + task.toString();
                            }
                            break;
                        case "getEpicTasks":
                            for (EpicTask task : manager.getEpicTasks().values()) {
                                response = response + "\n" + task.toString();
                            }
                            break;
                        case "getHistory":
                            for (Task task : manager.getHistory()) {
                                response = response + "\n" + task.toString();
                            }
                            break;
                        case "getSubTasks":
                            for (EpicTask task : manager.getEpicTasks().values()) {
                                for (SubTask sub : task.getSubTasks().values()) {
                                    response = response + "\n" + sub.toString();
                                }
                            }
                            break;
                        case "getTaskById":
                            String [] query = httpExchange.getRequestURI()
                                    .getQuery().split("=");
                            try {
                                response =
                                        manager.getTaskById(Integer.parseInt(query[1]))
                                                .toString();
                            } catch (ManagerSaveException | NumberFormatException e) {
                                System.out.println("Во время выполнения запроса по адресу:"
                                        + httpExchange.getRequestURI() + " произошла ошибка\n"
                                        + e.getMessage() + "\n" + e.getStackTrace());
                                httpExchange.sendResponseHeaders(400, 0);
                            }
                            break;
                        case "getSubTasksByEpic":
                            query = httpExchange.getRequestURI()
                                    .getQuery().split("=");
                            try {
                                manager.getSubTasksByEpic(manager
                                        .getTaskById(Integer.parseInt(query[1])));
                            } catch (ManagerSaveException | NumberFormatException e) {
                                System.out.println("Во время выполнения запроса по адресу:"
                                        + httpExchange.getRequestURI() + " произошла ошибка\n"
                                        + e.getMessage() + "\n" + e.getStackTrace());
                                httpExchange.sendResponseHeaders(400, 0);
                            }
                            break;
                        default:
                            System.out.println("Вы ввели неверную команду");
                            httpExchange.sendResponseHeaders(405, 0);
                    }
                    break;
                case "POST":
                    InputStream inputStream = httpExchange.getRequestBody();
                    String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                    System.out.println(body);
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(Task.class, body);
                    JsonElement jsonElement = JsonParser
                            .parseString(body);
                    if(!jsonElement.isJsonObject()) { // проверяем, точно ли мы получили JSON-объект
                        System.out.println("Ответ от сервера не соответствует ожидаемому.");
                        httpExchange.sendResponseHeaders(400, 0);
                        return;
                    }
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    Gson gson = new Gson();
                    Task task = gson.fromJson(jsonObject, Task.class);
                    try {
                        manager.putTask(task);
                    } catch (IntersectionException e) {
                        System.out.println("Во время выполнения запроса по адресу:"
                                + httpExchange.getRequestURI() + " произошла ошибка\n"
                                + e.getMessage() + "\n" + e.getStackTrace());
                        httpExchange.sendResponseHeaders(400, 0);
                    } catch (ManagerSaveException e) {
                        System.out.println("Во время выполнения запроса по адресу:"
                                + httpExchange.getRequestURI() + " произошла ошибка\n"
                                + e.getMessage() + "\n" + e.getStackTrace());
                        httpExchange.sendResponseHeaders(400, 0);
                    }
                    break;
                case "DELETE":
                    switch(getPathFromDeleteRequest(httpExchange)) {
                        case "removeAllTasks":
                            try {
                                manager.removeAllTasks();
                                response = "Все задачи, эпики и подзадачи успешно удалены!";
                            } catch (ManagerSaveException e) {
                                System.out.println("Во время выполнения запроса по адресу:"
                                        + httpExchange.getRequestURI() + " произошла ошибка\n"
                                        + e.getMessage() + "\n" + e.getStackTrace());
                                httpExchange.sendResponseHeaders(400, 0);
                            }
                            break;
                        case "removeTaskById":
                            String [] query = httpExchange.getRequestURI()
                                    .getQuery().split("=");
                            try {
                                manager.removeTaskById(Integer.parseInt(query[1]));
                                response = "Задача с id " + Integer.parseInt(query[1]) + " " +
                                        "удалена!";
                            } catch (ManagerSaveException e) {
                                System.out.println("Во время выполнения запроса по адресу:"
                                        + httpExchange.getRequestURI() + " произошла ошибка\n"
                                        + e.getMessage() + "\n" + e.getStackTrace());
                                httpExchange.sendResponseHeaders(400, 0);
                            }
                            break;
                        default:
                            System.out.println("Вы ввели неверную команду");
                    }
                    break;
                default:
                    response = "Некорректный метод!";
                    httpExchange.sendResponseHeaders(400, 0);
                    break;
            }

            httpExchange.sendResponseHeaders(200, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }

        private String getPathFromGetRequest(HttpExchange httpExchange) {
            String command = null;
            String path = httpExchange.getRequestURI().getPath();
            String [] parameters = path.split("/");
            if (httpExchange.getRequestURI().getQuery() == null) {
                switch (parameters[2]) {
                    case "task":
                        command = "getSingleTasks";
                        break;
                    case "epic":
                        command = "getEpicTasks";
                        break;
                    case "subtask":
                        command = "getSubTasks";
                        break;
                    case "history":
                        command = "getHistory";
                        break;
                }
            } else {
                if (parameters.length == 4 && parameters[3].equals("epic")) {
                    command = "getSubTasksByEpic";
                } else {
                    command = "getTaskById";
                }
            }
            return command;
        }

        private String getPathFromDeleteRequest(HttpExchange httpExchange) {
            String command;
            if (httpExchange.getRequestURI().getQuery() == null) {
                command = "removeAllTasks";
            } else {
                command = "removeTaskById";
            }
            return command;
        }
    }
}

