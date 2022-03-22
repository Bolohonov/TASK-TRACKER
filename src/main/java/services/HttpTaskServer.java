package services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import repository.ManagerSaveException;
import repository.Managers;
import repository.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

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

    private static final TaskManager managerToFile = managers.getTaskManagerToFile();

    private void run() throws IOException {
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler());
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        httpServer.stop(1);
    }

    static class TaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response;
            String method = httpExchange.getRequestMethod();

            switch(method) {
                case "GET":
                    switch(getBodyFromGetRequest(httpExchange)) {
                        case "getSingleTasks":
                            managerToFile.getSingleTasks();
                            break;
                        case "getEpicTasks":
                            managerToFile.getEpicTasks();
                            break;
                        case "getHistory":
                            managerToFile.getHistory();
                            break;
                        case "getTaskById":
                            String path = httpExchange.getRequestURI().getPath();
                            String [] parameters = path.split("/");
                            try {
                                managerToFile.getTaskById(Integer.parseInt(parameters[3]));
                            } catch (ManagerSaveException | NumberFormatException e) {
                                System.out.println(e.getMessage() + " " + e.getStackTrace());
                            }
                            break;
                        case "getSubTasksByEpic":
                            path = httpExchange.getRequestURI().getPath();
                            parameters = path.split("/");
                            try {
                                managerToFile.getSubTasksByEpic(managerToFile
                                        .getTaskById(Integer.parseInt(parameters[4])));
                            } catch (ManagerSaveException | NumberFormatException e) {
                                System.out.println(e.getMessage() + " " + e.getStackTrace());
                            }
                            break;
                        default:
                            System.out.println("Вы ввели неверную команду");
                    }
                    break;
                case "POST":
                    JsonElement jsonElement = JsonParser.parseString(httpExchange.getResponseBody().toString());
                    if(!jsonElement.isJsonObject()) { // проверяем, точно ли мы получили JSON-объект
                        System.out.println("Ответ от сервера не соответствует ожидаемому.");
                        return;
                    }
                    // преобразуем результат разбора текста в JSON-объект
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    managerToFile.putTask(jsonObject);
                    break;
                case "DELETE":
                    switch(getBodyFromDeleteRequest(httpExchange)) {
                        case "removeAllTasks":
                            try {
                                managerToFile.removeAllTasks();
                            } catch (ManagerSaveException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "removeTaskById":
                            String path = httpExchange.getRequestURI().getPath();
                            String [] parameters = path.split("/");
                            try {
                                managerToFile.removeTaskById(Integer.parseInt(parameters[3]));
                            } catch (ManagerSaveException e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            System.out.println("Вы ввели неверную команду");
                    }
                    break;
                default:
                    response = "Некорректный метод!";
                    break;
            }

            httpExchange.sendResponseHeaders(200, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }

        private String getBodyFromGetRequest(HttpExchange httpExchange) throws IOException {
            String command = null;
            String path = httpExchange.getRequestURI().getPath();
            String [] parameters = path.split("/");
            if (parameters.length == 3 && parameters[2].equals("task")) {
                command = "getSingleTasks";
            } else {
                if (parameters.length == 3 && parameters[2].equals("epic")) {
                    command = "getEpicTasks";
                } else {
                    if (parameters.length == 3 && parameters[2].equals("history")) {
                        command = "getHistory";
                    }
                }
            }
            if (parameters.length == 4) {
                command = "getTaskById";
            }
            if (parameters.length == 5 && parameters[2].equals("subtask") && parameters[3].equals("epic")) {
                command = "getSubTasksByEpic";
            }
            return command;
        }

        private String getBodyFromDeleteRequest(HttpExchange httpExchange) throws IOException {
            String command = null;
            String path = httpExchange.getRequestURI().getPath();
            String [] parameters = path.split("/");
            if(parameters.length == 3) {
                command = "removeAllTasks";
            } else {
                if (parameters.length == 4) {
                    command = "removeTaskById";
                }
            }
            return command;
        }
    }
}

