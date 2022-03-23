import repository.ManagerSaveException;
import repository.Managers;
import services.HttpTaskServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ManagerSaveException, IOException {
        HttpTaskServer server = new HttpTaskServer();
        server.run();
    }
}

