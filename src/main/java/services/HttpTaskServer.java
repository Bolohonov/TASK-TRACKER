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
import java.net.URISyntaxException;

import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;

public class HttpTaskServer {

    private static final int PORT = 8077;
    private HttpServer httpServer = HttpServer.create();

    public HttpTaskServer() throws IOException {
    }

    private static Managers managers;

    static {
        try {
            managers = new Managers();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static final TaskManager manager = managers.getDefault();

    public void run() throws IOException {
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler());
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    static class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response = new String();
            String method = httpExchange.getRequestMethod();

            switch (method) {
                case "GET":
                    switch (getPathFromGetRequest(httpExchange)) {
                        case "getSingleTasks":
                            for (SingleTask task : manager.getSingleTasks().values()) {
                                response = response + "\n" + task.toString();
                            }
                            sendText(httpExchange, response);
                            break;
                        case "getEpicTasks":
                            for (EpicTask task : manager.getEpicTasks().values()) {
                                response = response + "\n" + task.toString();
                            }
                            sendText(httpExchange, response);
                            break;
                        case "getHistory":
                            for (Task task : manager.getHistory()) {
                                response = response + "\n" + task.toString();
                            }
                            sendText(httpExchange, response);
                            break;
                        case "getSubTasks":
                            for (EpicTask task : manager.getEpicTasks().values()) {
                                for (SubTask sub : task.getSubTasks().values()) {
                                    response = response + "\n" + sub.toString();
                                }
                            }
                            sendText(httpExchange, response);
                            break;
                        case "getTaskById":
                            String[] query = httpExchange.getRequestURI()
                                    .getQuery().split("=");
                            try {
                                Task task = manager.getTaskById(Integer.parseInt(query[1]));
                                if (task != null) {
                                    response = task.toString();
                                    sendText(httpExchange, response);
                                } else {
                                    System.out.println("Задача с таким ID отсутствует!");
                                    httpExchange.sendResponseHeaders(404, 0);
                                    try (OutputStream os = httpExchange.getResponseBody()) {
                                        os.write(null);
                                    }
                                }
                            } catch (IOException | ManagerSaveException | NumberFormatException e) {
                                System.out.println("Во время выполнения запроса по адресу:"
                                        + httpExchange.getRequestURI() + " произошла ошибка\n"
                                        + e.getMessage() + "\n" + e.getStackTrace());
                                httpExchange.sendResponseHeaders(404, 0);
                            }
                            break;
                        case "getSubTasksByEpic":
                            query = httpExchange.getRequestURI()
                                    .getQuery().split("=");
                            try {
                                Task task = manager.getTaskById(Integer.parseInt(query[1]));
                                if (task != null) {
                                    response = task.toString();
                                    sendText(httpExchange, response);
                                } else {
                                    System.out.println("Задача с таким ID отсутствует!");
                                    httpExchange.sendResponseHeaders(404, 0);
                                    try (OutputStream os = httpExchange.getResponseBody()) {
                                        os.write(null);
                                    }
                                }
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
                    Gson gson = ConfigTaskJsonAdapter.getGsonBuilder().create();
                    Task task = gson.fromJson(body, Task.class);
                    try {
                        manager.putTask(task);
                        sendText(httpExchange, response);
                    } catch (IntersectionException | ManagerSaveException e) {
                        System.out.println("Во время выполнения запроса по адресу:"
                                + httpExchange.getRequestURI() + " произошла ошибка\n"
                                + e.getMessage() + "\n" + e.getStackTrace());
                        httpExchange.sendResponseHeaders(500, 0);
                    }
                    break;
                case "DELETE":
                    switch (getPathFromDeleteRequest(httpExchange)) {
                        case "removeAllTasks":
                            try {
                                manager.removeAllTasks();
                                response = "Все задачи, эпики и подзадачи успешно удалены!";
                                sendText(httpExchange, response);
                            } catch (IOException | ManagerSaveException e) {
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
                                sendText(httpExchange, response);
                            } catch (IOException | ManagerSaveException e) {
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
                    sendText(httpExchange, response);
                    break;
            }

//            try (OutputStream os = httpExchange.getResponseBody()) {
//                os.write(response.getBytes());
//            }
        }

        private String getPathFromGetRequest(HttpExchange httpExchange) {
            String command = null;
            String path = httpExchange.getRequestURI().getPath();
            String[] parameters = path.split("/");
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

        protected void sendText(HttpExchange h, String text) throws IOException {
            h.getResponseHeaders().add("Content-Type", "application/json");
            h.sendResponseHeaders(200, 0);
            try (OutputStream os = h.getResponseBody()) {
                os.write(text.getBytes());
            }
        }

    }

    public void stop(int delay) {
        httpServer.stop(delay);
    }
}

