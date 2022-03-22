package services;

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
                            ////////////////////////
                    }
                    break;
                case "POST":
                    getBody(httpExchange);

                    // извлеките заголовок
                    boolean flag = false;
                    Headers requestHeaders = httpExchange.getRequestHeaders();
                    if (requestHeaders.keySet().contains("X-Wish-Good-Day=true")) {
                        flag = true;
                    }
                    for (List<String> l : requestHeaders.values()) {
                        if (l.stream().anyMatch(o -> o.equals("X-Wish-Good-Day=true"))) {
                            flag = true;
                        }
                        //List<String> wishGoodDay = ...
                    }
                    // соберите ответ
                    if (flag) {
                        response = body + ", " + profession + " " + name + "!" + " Хорошего дня!";
                    } else {
                        response = body + ", " + profession + " " + name + "!";
                    }
                    break;
                case "DELETE":
                    response = "Здравствуйте!";
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
            InputStream input = httpExchange.getRequestBody();
            String body = new String(input.readAllBytes());
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

        private String getBodyFromPostRequest(HttpExchange httpExchange) throws IOException {
            String command = null;
            return command;
        }
    }
}

