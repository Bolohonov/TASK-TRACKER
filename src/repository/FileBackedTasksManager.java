package repository;

import tasks.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
            Repository<Task> rep = new Repository();
            rep.getTasks().putAll(memoryTasksManager.getSingleTasks());
            rep.getTasks().putAll(memoryTasksManager.getEpicTasks());
            rep.getTasks().entrySet().stream().sorted(Map.Entry.comparingByKey());
            rep.getTasks().values().stream().forEach(o -> {
                try {
                    fileWriter.write(o.toStringToFile());
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
                Arrays.stream(ch).forEach(System.out::println);
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
