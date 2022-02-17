package repository;

import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTasksManager implements TaskManager{

    private File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public void save(Task task) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            fileWriter.write()
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
                while (!s.isBlank()) {

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
        save(task);
    }

    @Override
    public boolean updateTask(Task task) {
        boolean isUpdate = false;
        if (task != null) {
            try {
                if (singleTaskRepository.getTasks().containsKey(task.getId())
                        && singleTaskRepository.getTasks().get(task.getId()).equals(task)) {
                    isUpdate = true;
                    historyManager.add(task);
                }
            } catch (ClassCastException exp) {
            }
            try {
                if (epicTaskRepository.getTasks().containsKey(task.getId())
                        && epicTaskRepository.getTasks().get(task.getId()).equals(task)) {
                    isUpdate = true;
                    historyManager.add(task);
                }
            } catch (ClassCastException exp) {
            }
            try {
                SubTask subTask = (SubTask) task;
                if (getSubTaskOrNullById(subTask.getId()).equals(task)) {
                    isUpdate = true;
                    historyManager.add(task);
                }
            } catch (ClassCastException exp) {
            }
        } else {
            System.out.println("Задача не обновлена!");
        }
        return isUpdate;
    }

    @Override
    public void removeAllTasks() {
        try {
            singleTaskRepository.removeAllTasks();
        } catch (NullPointerException exp) {
            System.out.println("В списке не было задач");
        }
        try {
            epicTaskRepository.removeAllTasks();
        } catch (NullPointerException exp) {
            System.out.println("В списке не было задач");
        }
        historyManager.clearHistory();
    }

    @Override
    public void removeTaskById(int id) {
        if (singleTaskRepository.getTasks().containsKey(id)) {
            historyManager.remove(id);
            singleTaskRepository.getTasks().remove(id);
        }
        if (epicTaskRepository.getTasks().containsKey(id)) {
            EpicTask epic = epicTaskRepository.getTasks().get(id);
            Map<Integer, SubTask> subTaskMap = epic.getSubTasks();
            for (Integer subTaskId : subTaskMap.keySet()) {
                historyManager.remove(subTaskId);
            }
            historyManager.remove(id);
            epicTaskRepository.getTasks().remove(id);
        }
        if (getSubTaskOrNullById(id) != null) {
            historyManager.remove(id);
            SubTask subTask = (SubTask) getSubTaskOrNullById(id);
            subTask.getEpicTask().removeSubTask(subTask);
        }
    }
}
