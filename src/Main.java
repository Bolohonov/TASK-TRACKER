import repository.EpicStatus;
import repository.RepositoryTaskManager;
import repository.TaskStatus;
import repository.TaskRepository;
import tasks.Task;

public class Main {
    public static void main(String[] args) {
        Task task = new Task("1","2",0, TaskStatus.NEW, EpicStatus.EPIC);
        new RepositoryTaskManager<>(task).setObjectToRepository();
        Task task2 = new Task("2","3",0, TaskStatus.NEW, EpicStatus.EPIC);
        new RepositoryTaskManager<>(task2).setObjectToRepository();
        TaskRepository.getTasks().forEach((Task t) -> System.out.println(task));
//        CommandManager commandManager = new CommandManager();
//        commandManager.getCommandPanel();
    }
}

