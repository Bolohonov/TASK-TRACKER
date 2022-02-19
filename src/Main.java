import repository.FileBackedTasksManager;
import service.Managers;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.io.File;
import java.nio.file.Paths;
//Привет, Ильдар! Прошу прощения, что убрал тестирующий код
// и вероятно загрузил неработающую версию, хотя рабочий вариант проверял кодом ниже.
//Мне остались не совсем понятны два замечания - оставил комментарии в коде - в классах Task,FileBackedTasksManager

public class Main {
    public static void main(String[] args) {
        //Создаю только, чтобы вызывать Print.
        Managers manager = new Managers();

        File file = Paths.get("./resources/data.csv").toFile();
        FileBackedTasksManager f = new FileBackedTasksManager(file);
        Task t = new SingleTask("name1", "desc1", f.getId());
        EpicTask t2 = new EpicTask("name2", "desc2", f.getId());
        Task t3 = new SubTask(t2,"name3", "desc3", f.getId());
        Task t4 = new SubTask(t2,"name4", "desc4", f.getId());
        EpicTask t5 = new EpicTask("name5", "desc5", f.getId());
        Task t6 = new SubTask(t5,"name6", "desc6", f.getId());
        Task t7 = new SubTask(t5,"name7", "desc7", f.getId());
        Task t8 = new SubTask(t2,"name8", "desc8", f.getId());
        Task t9 = new SubTask(t2,"name9", "desc9", f.getId());

        f.putTask(t);
        f.putTask(t2);
        f.putTask(t3);
        f.putTask(t4);
        f.putTask(t5);
        f.putTask(t6);
        f.putTask(t7);
        f.putTask(t8);
        f.putTask(t9);
        f.getTaskById(2);
        f.getTaskById(3);
        f.getTaskById(1);
        f.getTaskById(4);
        f.getTaskById(7);
        f.getTaskById(9);
        f.getTaskById(8);
        f.getTaskById(6);
        f.getTaskById(5);
        f.removeTaskById(6);
        f.removeTaskById(5);
        FileBackedTasksManager f1 = new FileBackedTasksManager(file);
        f1.loadFromFile(file);
        System.out.println("Проверка истории! (Эпик 5 и его подзадачи 6,7 - удалены)");
        manager.getPrinter().printList(f1.getHistory());
        System.out.println("-------------------------------------");
        System.out.println("Проверка эпиков!");
        manager.getPrinter().printMap(f1.getEpicTasks());
        System.out.println("-------------------------------------");
        System.out.println("Проверка подзадач!");
        manager.getPrinter().printMap(f1.getSubTasksByEpic(f1.getTaskById(2)));
        System.out.println("-------------------------------------");
        System.out.println("Проверка выгрузки отдельных задач!");
        manager.getPrinter().printTask(f1.getTaskById(2));
        manager.getPrinter().printTask(f1.getTaskById(1));
        manager.getPrinter().printTask(f1.getTaskById(3));
        manager.getPrinter().printTask(f1.getTaskById(6));

    }
}

