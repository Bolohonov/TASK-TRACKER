package services;

import com.google.gson.*;
import repository.ManagerSaveException;
import repository.Managers;
import repository.TaskStatus;
import tasks.*;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class TaskJsonAdapter implements JsonSerializer<Task>, JsonDeserializer<Task> {

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

    @Override
    public Task deserialize(JsonElement json, Type type,
                            JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement jsonElementDurationValue = jsonObject.get("duration");
        JsonObject jsonObjectDuration = jsonElementDurationValue.getAsJsonObject();
        Duration duration = Duration.ZERO;
        if(jsonObjectDuration.has("days")) {
            Duration durationOfDays = Duration
                    .ofDays(jsonObjectDuration.get("days").getAsLong());
            duration = duration.plus(Duration
                    .ofDays(jsonObjectDuration.get("days").getAsLong()));
        }
        if(jsonObjectDuration.has("hours")) {
            duration.plus(Duration
                    .ofHours(jsonObjectDuration.get("hours").getAsLong()));
        }
        if(jsonObjectDuration.has("minutes")) {
            duration.plus(Duration
                    .ofMinutes(jsonObjectDuration.get("minutes").getAsLong()));
        }
        if(jsonObjectDuration.has("seconds")) {
            duration.plus(Duration
                    .ofSeconds(jsonObjectDuration.get("seconds").getAsLong()));
        }
        if(jsonObjectDuration.has("nanos")) {
            duration.plus(Duration
                    .ofNanos(jsonObjectDuration.get("nanos").getAsLong()));
        }
        jsonObject = json.getAsJsonObject();
        JsonElement jsonElementLocalDateTime = jsonObject.get("startTime");
        JsonObject jsonObjectLocalDateTime = jsonElementLocalDateTime.getAsJsonObject();
        LocalDate localDate = null;
        LocalTime localTime = null;
        if(jsonObjectLocalDateTime.has("year")
                && jsonObjectLocalDateTime.has("month")
                && jsonObjectLocalDateTime.has("day") ) {
            localDate = LocalDate.of(
                    jsonObjectLocalDateTime.get("year").getAsInt(),
                    jsonObjectLocalDateTime.get("month").getAsInt(),
                    jsonObjectLocalDateTime.get("day").getAsInt());
        }
        if(jsonObjectLocalDateTime.has("hour")
                && jsonObjectLocalDateTime.has("minute")) {
             localTime = LocalTime.of(
                    jsonObjectLocalDateTime.get("hour").getAsInt(),
                    jsonObjectLocalDateTime.get("minute").getAsInt());
        }
        if(jsonObjectLocalDateTime.has("hour")
                && jsonObjectLocalDateTime.has("minute")
        && jsonObjectLocalDateTime.has("second")) {
            localTime = LocalTime.of(
                    jsonObjectLocalDateTime.get("hour").getAsInt(),
                    jsonObjectLocalDateTime.get("minute").getAsInt(),
                    jsonObjectLocalDateTime.get("second").getAsInt());
        }
        if(jsonObjectLocalDateTime.has("hour")
                && jsonObjectLocalDateTime.has("minute")
                && jsonObjectLocalDateTime.has("second")
                && jsonObjectLocalDateTime.has("nano")) {
            localTime = LocalTime.of(
                    jsonObjectLocalDateTime.get("hour").getAsInt(),
                    jsonObjectLocalDateTime.get("minute").getAsInt(),
                    jsonObjectLocalDateTime.get("second").getAsInt(),
                    jsonObjectLocalDateTime.get("nano").getAsInt());
        }
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        jsonObject = json.getAsJsonObject();
        Task task = null;
        String taskTypeFromJson = jsonObject.get("type").getAsString();
        if (taskTypeFromJson.equals(TaskType.TASK.toString())) {
            task = new SingleTask(
                    jsonObject.get("name").getAsString(),
                    jsonObject.get("description").getAsString(),
                    jsonObject.get("id").getAsInt(),
                    Optional.of(duration),
                    Optional.of(localDateTime));
            task.setStatus(TaskStatus.valueOf(jsonObject.get("status")
                    .getAsString()));
        } else {
            if (taskTypeFromJson.equals(TaskType.EPIC.toString())) {
                task = new EpicTask(
                        jsonObject.get("name").getAsString(),
                        jsonObject.get("description").getAsString(),
                        jsonObject.get("id").getAsInt());
            } else {
                if (taskTypeFromJson.equals(TaskType.SUBTASK.toString())) {
                    int epicId = jsonObject.get("epic").getAsInt();
                    try {
                        EpicTask epic = (EpicTask) new Managers()
                                .getDefault().getTaskById(epicId);
                        task = new SubTask(epic,
                                jsonObject.get("name").getAsString(),
                                jsonObject.get("description").getAsString(),
                                jsonObject.get("id").getAsInt(),
                                Optional.of(duration),
                                Optional.of(localDateTime));
                    } catch (ManagerSaveException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return task;
    }
}
