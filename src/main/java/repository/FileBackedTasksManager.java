package repository;

import tasks.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class FileBackedTasksManager extends InMemoryTasksManager implements TaskManager {

    private File file;
    private static final String TABLE_HEADER
            = "id,type,name,status,description,duration,startTime,epic";

    public FileBackedTasksManager(Path path) throws ManagerSaveException {
        this.file = path.toFile();
        load(file);
    }

    public void save() throws ManagerSaveException {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file,
                StandardCharsets.UTF_8))) {
            Repository<Task> rep = new Repository<>();
            fileWriter.write(TABLE_HEADER + System.lineSeparator());
            rep.getTasks().putAll(super.getSingleTasks());
            rep.getTasks().putAll(super.getEpicTasks());
            super.getEpicTasks().values().stream()
                    .forEach(o -> rep.getTasks().putAll(o.getSubTasks()));
            HashMap<Integer, Task> map = rep.getTasks()
                    .entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors
                            .toMap(Map.Entry::getKey,
                                    Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            map.values().stream().forEach(o -> {
                try {
                    fileWriter.append(o.toString(o) + System.lineSeparator());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            if (historyManager.getHistory() != null && !historyManager.getHistory().isEmpty()) {
                fileWriter.append(System.lineSeparator());
                fileWriter.append(toString(historyManager));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка во время сохранения файла!");
        }
    }

    public void load(File file) throws ManagerSaveException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file,
                StandardCharsets.UTF_8))) {
            while (fileReader.ready()) {
                String s = fileReader.readLine();
                if (!s.isBlank()) {
                    Task task = fromString(s);
                    if (task != null) {
                        super.putTask(task);
                    }
                } else {
                    while (fileReader.ready()) {
                        s = fileReader.readLine();
                        if (!s.equals(null) && !s.isBlank()
                                && !s.equals(TABLE_HEADER)) {
                            fromStringToHistory(s);
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка во время загрузки файла!");
        } catch (IntersectionException e) {
            System.out.println("Совпадение временного интервала во время загрузки из файла!");;
        }
    }

    private Task fromString(String value) {
        Task task = null;
        String[] values = null;
        if (!value.isBlank() && !value.equals(TABLE_HEADER)) {
            values = value.split(",");
        }
        if (values != null) {
            try {
                if (values[1].equals(TaskType.TASK.toString())) {
                    task = new SingleTask(values[2], values[4], Integer.parseInt(values[0]),
                            Optional.of(Duration.parse(values[5])),
                            Optional.of(LocalDateTime.parse(values[6])));
                    task.setStatus(TaskStatus.valueOf(values[3]));
                } else if (values[1].equals(TaskType.EPIC.toString())) {
                    task = new EpicTask(values[2], values[4], Integer.parseInt(values[0]));
                } else if (values[1].equals(TaskType.SUBTASK.toString())) {
                    task = new SubTask((EpicTask) super
                            .getTaskById(Integer.parseInt(values[7])),
                            values[2], values[4], Integer.parseInt(values[0]),
                            Optional.of(Duration.parse(values[5])),
                            Optional.of(LocalDateTime.parse(values[6])));
                    task.setStatus(TaskStatus.valueOf(values[3]));
                }
            } catch (DateTimeParseException | ManagerSaveException exp) {
                System.out.println(exp.getMessage());
            }
        }
        return task;
    }

    private static String toString(HistoryManager manager) {
        StringBuilder historyToString = new StringBuilder();
        if (!manager.getHistory().isEmpty() && manager.getHistory() != null) {
            for (Task task : manager.getHistory()) {
                historyToString.append(task.getId() + ",");
            }
        }
        return historyToString.toString();
    }

    private void fromStringToHistory(String value) throws ManagerSaveException{
        String[] values = value.split(",");
        if (values != null && values.length != 0) {
            for (int i = 0; i < values.length; i++) {
                try {
                    super.getHistory().add(super.getTaskById(Integer.parseInt(values[i])));
                } catch (NumberFormatException exp) {
                    System.out.println("Некорректный список истории просмотров!");
                }
            }
        }
    }

    @Override
    public void putTask(Task task) throws IntersectionException, ManagerSaveException {
        super.putTask(task);
        save();
    }

    @Override
    public boolean updateTask(Task task) throws ManagerSaveException {
        boolean result = super.updateTask(task);
        save();
        return result;
    }

    @Override
    public Task getTaskById(int id) throws ManagerSaveException {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public void removeAllTasks() throws ManagerSaveException {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeTaskById(int id) throws ManagerSaveException {
        super.removeTaskById(id);
        save();
    }
}
