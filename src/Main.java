import repository.FileBackedTasksManager;
import service.CommandManager;

import java.io.File;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        //CommandManager commandManager = new CommandManager();
        //commandManager.getCommandPanel();
        File file = Paths.get("./resources/data.csv").toFile();
        FileBackedTasksManager f = new FileBackedTasksManager(file);
        f.main();
    }
}

