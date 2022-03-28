package services;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import repository.ManagerSaveException;
import repository.TaskStatus;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.lang.reflect.Type;

public class TaskSerializer implements JsonSerializer<Task> {

    @Override
    public JsonElement serialize(Task task, Type type, JsonSerializationContext jsonSerializationContext) {
        Gson gson =
                new GsonBuilder()
                        .setExclusionStrategies(new ExclusionStrategyOfTask())
                        .create();
        JsonElement jsonElement = gson.toJsonTree(task);
        jsonElement.getAsJsonObject().addProperty("type", task.getType().toString());
        if (task instanceof SubTask) {
            jsonElement.getAsJsonObject().addProperty("epic",
                    ((SubTask) task).getEpicTask().getId());
        }
        return jsonElement;
    }
}
