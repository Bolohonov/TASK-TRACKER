import repository.ManagerSaveException;
import repository.Managers;
import services.HttpTaskServer;
import services.KVServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws ManagerSaveException, IOException {
        new HttpTaskServer().run();
        new KVServer().start();
    }
}

