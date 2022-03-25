package services;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import repository.ManagerSaveException;
import repository.TaskStatus;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class TaskSerializer implements JsonSerializer<Task> {

    @Override
    public JsonElement serialize(Task task, Type type, JsonSerializationContext jsonSerializationContext) {
        Gson gson = new Gson();
        JsonElement json = null;
        if (task instanceof SingleTask) {
            String str = gson.toJson(task);
            json = gson.fromJson(str, JsonElement.class);
        } else {
            if (task instanceof EpicTask) {
                String str = task.getName() + " " + task.getDescription();
                JsonWriter writer = null;
                try {
                    writer.jsonValue(task.getName()).jsonValue(task.getDescription());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (task instanceof SubTask) {
                    String str = task.getName() + " " + task.getDescription();
                    JsonWriter writer = null;
                    try {
                        writer.jsonValue(task.getName()).jsonValue(task.getDescription());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    json = gson.fromJson(str, JsonElement.class);
                } else {
                    try {
                        throw new ManagerSaveException("Неизвестный тип задачи!");
                    } catch (ManagerSaveException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return json;
    }
}
