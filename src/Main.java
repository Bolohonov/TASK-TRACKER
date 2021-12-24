import repository.EpicStatus;
import repository.Repository;
import repository.TaskStatus;
import repository.TaskStorage;
import tasks.Task;

public class Main {
    public static void main(String[] args) {
        Task task = new Task("1","2",0, TaskStatus.NEW, EpicStatus.EPIC);
        new Repository<>(task).setObjectToRepository();
        Task task2 = new Task("2","3",0, TaskStatus.NEW, EpicStatus.EPIC);
        new Repository<>(task2).setObjectToRepository();
        TaskStorage.getTasks().forEach((Task t) -> System.out.println(task));
//        CommandManager commandManager = new CommandManager();
//        commandManager.getCommandPanel();
    }
}

