package repository;

import tasks.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTasksManager implements TaskManager{

    private File file;
    private static final InMemoryTasksManager memoryTasksManager = new InMemoryTasksManager();

    public FileBackedTasksManager(File file) {
        this.file = file;
        loadFromFile(file);
    }

    public void save() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            Repository<Task> rep = new Repository<>();
            fileWriter.write("id,type,name,status,description,epic\n");
            rep.getTasks().putAll(memoryTasksManager.getSingleTasks());
            rep.getTasks().putAll(memoryTasksManager.getEpicTasks());
            memoryTasksManager.getEpicTasks().values().stream().forEach(o -> rep.getTasks().putAll(o.getSubTasks()));
            rep.getTasks().entrySet().stream().sorted(Map.Entry.comparingByKey());
            rep.getTasks().entrySet().forEach(System.out::println);
            rep.getTasks().values().stream().forEach(o -> {
                try {
                    fileWriter.append(o.toString(o) + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (FileNotFoundException e) {
            System.out.println("Произошла ошибка во время чтения файла.");
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время чтения файла.");
        }

    }

    private static void loadFromFile(File file) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            while(fileReader.readLine() != null && fileReader.ready()) {
                if (!fileReader.readLine().equals("id,type,name,status,description,epic")) {
                    String s = fileReader.readLine();
                    Task task = fromString(s);
                    memoryTasksManager.putTask(task);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Произошла ошибка во время чтения файла.");
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время чтения файла.");
        }
    }

    private static Task fromString(String value) {
        Task task = null;
        String [] ch = null;
        if (!value.isBlank() && !value.equals("id,type,name,status,description,epic")) {
            ch = value.split(",");
        }
        if (ch != null) {
            if (ch[1].equals(TaskType.TASK.toString())) {
               task = new SingleTask(Integer.parseInt(ch[0]),
                        ch[2], TaskStatus.valueOf(ch[3]), ch[4]);
            } else if (ch[1].equals(TaskType.EPIC.toString())) {
                task = new EpicTask(Integer.parseInt(ch[0]),
                        ch[2], TaskStatus.valueOf(ch[3]), ch[4]);
            } else if (ch[1].equals(TaskType.SUBTASK.toString())) {
                task = new SubTask(Integer.parseInt(ch[0]),
                        ch[2], TaskStatus.valueOf(ch[3]), ch[4], memoryTasksManager.getEpicTasks().get(ch[5]));
            }
        } else {
            System.out.println("Строка не распознана!");
        }
        return task;
    }

    @Override
    public void putTask(Task task) {
        super.putTask(task);
        save();
    }

    @Override
    public boolean updateTask(Task task) {
        boolean result = super.updateTask(task);
        save();
        return result;
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }
}
