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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;

public class HttpTaskServer {

    private static final int PORT = 8077;
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
        Gson gson = new GsonBuilder().registerTypeAdapter(SingleTask.class, new TaskSerializer()).create();
        Task task = manager.getTaskById(1007);
        System.out.println(gson.toJson(task));
//        SubTask task = gson.fromJson(taskStr, SubTask.class);
//        System.out.println(task.toString());

        //httpServer.stop(1);
    }

    static class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = "Ответ на запрос: ";
            String method = httpExchange.getRequestMethod();

            switch (method) {
                case "GET":
                    switch (getPathFromGetRequest(httpExchange)) {
                        case "getSingleTasks":
                            for (SingleTask task : manager.getSingleTasks().values()) {
                                response = response + "\n" + task.toString();
                            }
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        case "getEpicTasks":
                            for (EpicTask task : manager.getEpicTasks().values()) {
                                response = response + "\n" + task.toString();
                            }
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        case "getHistory":
                            for (Task task : manager.getHistory()) {
                                response = response + "\n" + task.toString();
                            }
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        case "getSubTasks":
                            for (EpicTask task : manager.getEpicTasks().values()) {
                                for (SubTask sub : task.getSubTasks().values()) {
                                    response = response + "\n" + sub.toString();
                                }
                            }
                            httpExchange.sendResponseHeaders(200, 0);
                            break;
                        case "getTaskById":
                            String[] query = httpExchange.getRequestURI()
                                    .getQuery().split("=");
                            try {
                                response =
                                        manager.getTaskById(Integer.parseInt(query[1]))
                                                .toString();
                                httpExchange.sendResponseHeaders(200, 0);
                            } catch (ManagerSaveException | NumberFormatException e) {
                                System.out.println("Во время выполнения запроса по адресу:"
                                        + httpExchange.getRequestURI() + " произошла ошибка\n"
                                        + e.getMessage() + "\n" + e.getStackTrace());
                                httpExchange.sendResponseHeaders(500, 0);
                            }
                            break;
                        case "getSubTasksByEpic":
                            query = httpExchange.getRequestURI()
                                    .getQuery().split("=");
                            try {
                                manager.getSubTasksByEpic(manager
                                        .getTaskById(Integer.parseInt(query[1])));
                                httpExchange.sendResponseHeaders(200, 0);
                            } catch (ManagerSaveException | NumberFormatException e) {
                                System.out.println("Во время выполнения запроса по адресу:"
                                        + httpExchange.getRequestURI() + " произошла ошибка\n"
                                        + e.getMessage() + "\n" + e.getStackTrace());
                                httpExchange.sendResponseHeaders(500, 0);
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
                    Gson gson = new Gson();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(Task.class, new TaskDeserializer());

                    switch (getPathFromPostRequest(httpExchange)) {
                        case "task":
                            SingleTask task = gson.fromJson(body, SingleTask.class);
                            try {
                                manager.putTask(task);
                                response = "Задача сохранена: " + "\n" + task.toString();
                                httpExchange.sendResponseHeaders(200, 0);
                            } catch (IntersectionException e) {
                                System.out.println("Во время выполнения запроса по адресу:"
                                        + httpExchange.getRequestURI() + " произошла ошибка\n"
                                        + e.getMessage() + "\n" + e.getStackTrace());
                                httpExchange.sendResponseHeaders(500, 0);
                            } catch (ManagerSaveException e) {
                                System.out.println("Во время выполнения запроса по адресу:"
                                        + httpExchange.getRequestURI() + " произошла ошибка\n"
                                        + e.getMessage() + "\n" + e.getStackTrace());
                                httpExchange.sendResponseHeaders(500, 0);
                            }
                            break;
                        case "epic":
                            EpicTask epic = gson.fromJson(body, EpicTask.class);
                            try {
                                manager.putTask(epic);
                                response = "Задача сохранена: " + "\n" + epic.toString();
                                httpExchange.sendResponseHeaders(200, 0);
                            } catch (IntersectionException e) {
                                System.out.println("Во время выполнения запроса по адресу:"
                                        + httpExchange.getRequestURI() + " произошла ошибка\n"
                                        + e.getMessage() + "\n" + e.getStackTrace());
                                httpExchange.sendResponseHeaders(500, 0);
                            } catch (ManagerSaveException e) {
                                System.out.println("Во время выполнения запроса по адресу:"
                                        + httpExchange.getRequestURI() + " произошла ошибка\n"
                                        + e.getMessage() + "\n" + e.getStackTrace());
                                httpExchange.sendResponseHeaders(500, 0);
                            }
                            break;
                        case "subTask":
                            SubTask subTask = gson.fromJson(body, SubTask.class);
                            try {
                                manager.putTask(subTask);
                                response = "Задача сохранена: " + "\n" + subTask.toString();
                                httpExchange.sendResponseHeaders(200, 0);
                            } catch (IntersectionException e) {
                                System.out.println("Во время выполнения запроса по адресу:"
                                        + httpExchange.getRequestURI() + " произошла ошибка\n"
                                        + e.getMessage() + "\n" + e.getStackTrace());
                                httpExchange.sendResponseHeaders(500, 0);
                            } catch (ManagerSaveException e) {
                                System.out.println("Во время выполнения запроса по адресу:"
                                        + httpExchange.getRequestURI() + " произошла ошибка\n"
                                        + e.getMessage() + "\n" + e.getStackTrace());
                                httpExchange.sendResponseHeaders(500, 0);
                            }
                            break;
                        default:
                            System.out.println("Вы ввели неверную команду");
                            httpExchange.sendResponseHeaders(405, 0);
                    }
                            break;
                case "DELETE":
                    switch (getPathFromDeleteRequest(httpExchange)) {
                        case "removeAllTasks":
                            try {
                                manager.removeAllTasks();
                                response = "Все задачи, эпики и подзадачи успешно удалены!";
                                httpExchange.sendResponseHeaders(200, 0);
                            } catch (ManagerSaveException e) {
                                System.out.println("Во время выполнения запроса по адресу:"
                                        + httpExchange.getRequestURI() + " произошла ошибка\n"
                                        + e.getMessage() + "\n" + e.getStackTrace());
                                httpExchange.sendResponseHeaders(500, 0);
                            }
                            break;
                        case "removeTaskById":
                            String[] query = httpExchange.getRequestURI()
                                    .getQuery().split("=");
                            try {
                                manager.removeTaskById(Integer.parseInt(query[1]));
                                response = "Задача с id " + Integer.parseInt(query[1]) + " " +
                                        "удалена!";
                                httpExchange.sendResponseHeaders(200, 0);
                            } catch (ManagerSaveException e) {
                                System.out.println("Во время выполнения запроса по адресу:"
                                        + httpExchange.getRequestURI() + " произошла ошибка\n"
                                        + e.getMessage() + "\n" + e.getStackTrace());
                                httpExchange.sendResponseHeaders(500, 0);
                            }
                            break;
                        default:
                            System.out.println("Вы ввели неверную команду");
                            httpExchange.sendResponseHeaders(400, 0);
                    }
                    break;
                default:
                    response = "Некорректный метод!";
                    httpExchange.sendResponseHeaders(400, 0);
                    break;
            }
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

        private String getPathFromPostRequest(HttpExchange httpExchange) {
            String command = null;
            String path = httpExchange.getRequestURI().getPath();
            String[] parameters = path.split("/");
            if (httpExchange.getRequestURI().getQuery() == null) {
                switch (parameters[2]) {
                    case "task":
                        command = "task";
                        break;
                    case "epic":
                        command = "epic";
                        break;
                    case "subtask":
                        command = "subtask";
                        break;
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

    public void stop() {
        httpServer.stop(1);
    }
}

