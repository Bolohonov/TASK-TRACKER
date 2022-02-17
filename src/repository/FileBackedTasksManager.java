package repository;

import tasks.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FileBackedTasksManager extends InMemoryTasksManager implements TaskManager{

    private File file;
    private static final InMemoryTasksManager memoryTasksManager = new InMemoryTasksManager();

    public FileBackedTasksManager(File file) {
        this.file = file;
        loadFromFile(file);
    }

    public void save() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            Set<Task> set = new HashSet<>();
            Repository<Task> rep = new Repository<>();
            fileWriter.write("id,type,name,status,description,epic");
            rep.getTasks().putAll(memoryTasksManager.getSingleTasks());
            rep.getTasks().putAll(memoryTasksManager.getEpicTasks());
            memoryTasksManager.getEpicTasks().values().stream().forEach(o -> rep.getTasks().putAll(o.getSubTasks()));
            rep.getTasks().entrySet().stream().sorted(Map.Entry.comparingByKey());
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
            while(fileReader.ready()) {
                String s = fileReader.readLine();
                String [] ch = new String[] {""};
                if (!s.isBlank()) {
                    ch = s.split(",");
                }
                if (ch[1].equals(TaskType.TASK)) {
                    memoryTasksManager.putTask(new SingleTask(Integer.parseInt(ch[0]),
                            ch[2], TaskStatus.valueOf(ch[3]), ch[4]));
                } else if (ch[1].equals(TaskType.EPIC)) {
                    memoryTasksManager.putTask(new EpicTask(Integer.parseInt(ch[0]),
                            ch[2], TaskStatus.valueOf(ch[3]), ch[4]));
                } else if (ch[1].equals(TaskType.SUBTASK)) {
                    SubTask subTask = new SubTask(Integer.parseInt(ch[0]),
                            ch[2], TaskStatus.valueOf(ch[3]), ch[4], memoryTasksManager.getEpicTasks().get(ch[5]));
                    memoryTasksManager.putTask(subTask);
                    memoryTasksManager.getEpicTasks().get(ch[5]).addSubTask(subTask);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Произошла ошибка во время чтения файла.");
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время чтения файла.");
        }
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
