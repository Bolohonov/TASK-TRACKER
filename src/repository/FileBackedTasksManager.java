package repository;

import tasks.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FileBackedTasksManager extends InMemoryTasksManager implements TaskManager{

    private File file;
    private static final InMemoryTasksManager memoryTasksManager = new InMemoryTasksManager();

    public FileBackedTasksManager(File file) {
        this.file = file;
        loadFromFile(file);
    }

    public void save() {
        try {
            try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
                Repository<Task> rep = new Repository<>();
                fileWriter.write("id,type,name,status,description,epic" + System.lineSeparator());
                rep.getTasks().putAll(memoryTasksManager.getSingleTasks());
                rep.getTasks().putAll(memoryTasksManager.getEpicTasks());
                memoryTasksManager.getEpicTasks().values().stream().forEach(o -> rep.getTasks().putAll(o.getSubTasks()));
                HashMap<Integer, Task> sortedMap = rep.getTasks().entrySet().stream().sorted(Map.Entry.comparingByKey())
                        .collect(Collectors
                                .toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                ;
                sortedMap.values().stream().forEach(o -> {
                    try {
                        fileWriter.append(o.toString(o) + System.lineSeparator());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (FileNotFoundException e) {
                throw new ManagerSaveException("Произошла ошибка во время чтения файла.");
            } catch (IOException e) {
                throw new ManagerSaveException("Произошла ошибка во время чтения файла.");
            }
        } catch (ManagerSaveException e) {
            System.out.println("Ошибка менеджера: " + e.getMessage());
        }
    }

    private static void loadFromFile(File file) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            while(fileReader.ready() && file.length() != 0) {
                String s = fileReader.readLine();
                Task task = fromString(s);
                if (task != null) {
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
                        ch[2], TaskStatus.valueOf(ch[3]), ch[4], (EpicTask)memoryTasksManager
                        .getTaskById(Integer.parseInt(ch[5])));
            }
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

    static void main(String[] args) {
        File file = Paths.get("./resources/data.csv").toFile();
        FileBackedTasksManager f = new FileBackedTasksManager(file);
        Task t = new SingleTask("name1", "desc1", getId());
        EpicTask t2 = new EpicTask("name2", "desc2", getId());
        Task t3 = new SubTask(t2,"name3", "desc3", getId());
        Task t4 = new SubTask(t2,"name4", "desc4", getId());

        f.putTask(t);
        f.putTask(t2);
        f.putTask(t3);
        f.putTask(t4);
    }
}
