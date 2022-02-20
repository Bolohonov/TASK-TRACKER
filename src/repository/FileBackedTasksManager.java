package repository;

import tasks.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FileBackedTasksManager extends InMemoryTasksManager implements TaskManager {

    private File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
        loadFromFile(file);
    }

    public void save() {
        try {
            try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
                Repository<Task> rep = new Repository<>();
                fileWriter.write("id,type,name,status,description,epic" + System.lineSeparator());
                rep.getTasks().putAll(super.getSingleTasks());
                rep.getTasks().putAll(super.getEpicTasks());
                super.getEpicTasks().values().stream()
                        .forEach(o -> rep.getTasks().putAll(o.getSubTasks()));
                //Сделал здесь сортировку по ключу-id, чтобы исключить вариант записи в файл задач
                // с нарушением последовательности id по возрастанию, например, когда подзадача будет записана
                // и считана раньше своего эпика.
                //Название было sortedMap, т.к. в итоге собирается в коллекцию LinkedHashMap.
                HashMap<Integer, Task> map = rep.getTasks()
                        .entrySet().stream().sorted(Map.Entry.comparingByKey())
                        .collect(Collectors
                                .toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                ;
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
                throw new ManagerSaveException("Произошла ошибка во время чтения файла.");
            }
        } catch (ManagerSaveException e) {
            System.out.println("Ошибка менеджера: " + e.getMessage());
        }
    }

    public void loadFromFile(File file) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
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
                        if (!s.equals(null) && !s.isBlank() && !s.equals("id,type,name,status,description,epic")) {
                            fromStringToHistory(s);
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время чтения файла.");
        }
    }

    private Task fromString(String value) {
        Task task = null;
        String[] values = null;
        if (!value.isBlank() && !value.equals("id,type,name,status,description,epic")) {
            values = value.split(",");
        }
        if (values != null) {
            if (values[1].equals(TaskType.TASK.toString())) {
                task = new SingleTask(Integer.parseInt(values[0]),
                        values[2], TaskStatus.valueOf(values[3]), values[4]);
            } else if (values[1].equals(TaskType.EPIC.toString())) {
                task = new EpicTask(Integer.parseInt(values[0]),
                        values[2], TaskStatus.valueOf(values[3]), values[4]);
            } else if (values[1].equals(TaskType.SUBTASK.toString())) {
                task = new SubTask(Integer.parseInt(values[0]),
                        values[2], TaskStatus.valueOf(values[3]), values[4], (EpicTask) super
                        .getTaskById(Integer.parseInt(values[5])));
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

    private void fromStringToHistory(String value) {
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
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
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
