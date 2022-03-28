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

public class SubTaskSerializer implements JsonSerializer<SubTask> {

    @Override
    public JsonElement serialize(SubTask task, Type type, JsonSerializationContext jsonSerializationContext) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(task);
        jsonElement.getAsJsonObject().addProperty("epicTask", task.getEpicTask().getId());
        jsonElement.getAsJsonObject().addProperty("type", task.getType().toString());
        jsonElement.getAsJsonObject().addProperty("name", task.getName());
        jsonElement.getAsJsonObject().addProperty("description", task.getDescription());
        jsonElement.getAsJsonObject().addProperty("id", task.getId());
        jsonElement.getAsJsonObject().addProperty("status", task.getStatus().toString());
        jsonElement.getAsJsonObject().addProperty("duration",
                task.getDuration().get().toString());
        jsonElement.getAsJsonObject().addProperty("startTime",
                task.getStartTime().get().toString());
        return jsonElement;
    }
}
