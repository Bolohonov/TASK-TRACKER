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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskJsonAdapter implements JsonSerializer<Task>, JsonDeserializer<Task> {

    @Override
    public JsonElement serialize(Task task, Type type,
                                 JsonSerializationContext jsonSerializationContext) {
        Gson gson =
                new GsonBuilder()
                        .setExclusionStrategies(new ExclusionStrategyOfTask())
                        .create();
        JsonElement jsonElement = gson.toJsonTree(task);
        jsonElement.getAsJsonObject().addProperty("type", task.getType().toString());
        if (task instanceof EpicTask) {
            List<Integer> list = new ArrayList<>();
            ((EpicTask) task)
                    .getSubTasks().values()
                    .forEach((o) -> list.add(o.getId()));
            if (!list.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (Integer id : list) {
                    sb.append(id + ",");
                }
                jsonElement.getAsJsonObject().addProperty("subTasksOfEpic", sb.toString());
            }
        }
        return jsonElement;
    }

    @Override
    public Task deserialize(JsonElement json, Type type,
                            JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Task task = null;
        Optional<Duration> durationOrZero;
        Duration duration = deserializeDuration(jsonObject);
        if(duration.isZero()) {
            durationOrZero = Optional.empty();
        } else {
            durationOrZero = Optional.of(duration);
        }
        Optional<LocalDateTime> timeOrZero;
        LocalDateTime localDateTime = deserializeLocalDateTime(jsonObject);
        if(localDateTime == null) {
            timeOrZero = Optional.empty();
        } else {
            timeOrZero = Optional.of(localDateTime);
        }
        String taskTypeFromJson = jsonObject.get("type").getAsString();
        if (taskTypeFromJson.equals(TaskType.TASK.toString())) {
            task = new SingleTask(
                    jsonObject.get("name").getAsString(),
                    jsonObject.get("description").getAsString(),
                    jsonObject.get("id").getAsInt(),
                    durationOrZero,
                    timeOrZero);
            task.setStatus(TaskStatus.valueOf(jsonObject.get("status")
                    .getAsString()));
        } else {
            if (taskTypeFromJson.equals(TaskType.EPIC.toString())) {
                task = new EpicTask(
                        jsonObject.get("name").getAsString(),
                        jsonObject.get("description").getAsString(),
                        jsonObject.get("id").getAsInt());
                task = deserializeSubTasksFromEpic((EpicTask)task, json);
            } else {
                if (taskTypeFromJson.equals(TaskType.SUBTASK.toString())) {
                    int epicId = jsonObject.get("epicId").getAsInt();
                    try {
                        EpicTask epic = (EpicTask) new Managers()
                                .getDefault().getTaskById(epicId);
                        task = new SubTask(epicId,
                                jsonObject.get("name").getAsString(),
                                jsonObject.get("description").getAsString(),
                                jsonObject.get("id").getAsInt(),
                                durationOrZero,
                                timeOrZero);
                    } catch (ManagerSaveException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return task;
    }

    private Duration deserializeDuration(JsonObject jsonObject) {
        Duration duration = Duration.ZERO;
        JsonObject jsonElementDuration = jsonObject.get("duration").getAsJsonObject();
        JsonElement jsonElementDurationValue = jsonElementDuration.get("value");
        if (jsonElementDurationValue != null) {
            JsonObject jsonObjectDuration = jsonElementDurationValue.getAsJsonObject();
            if(jsonObjectDuration.has("seconds")) {
                if (jsonObjectDuration.get("seconds").getAsLong() >=3600) {
                    duration =  Duration
                            .ofHours(jsonObjectDuration.get("seconds").getAsLong()/3600);
                } else {
                    if (jsonObjectDuration.get("seconds").getAsLong() >=60) {
                        duration = Duration
                                .ofMinutes(jsonObjectDuration.get("seconds").getAsLong()/60);
                    } else {
                        duration = Duration
                                .ofSeconds(jsonObjectDuration.get("seconds").getAsLong());
                    }
                }
            }
            if(jsonObjectDuration.has("nanos") && jsonObjectDuration.get("nanos").getAsLong() != 0L) {
                duration = Duration.ofNanos(jsonObjectDuration.get("nanos").getAsLong());
            }
        }
        return duration;
    }

    private LocalDateTime deserializeLocalDateTime(JsonObject jsonObject) {
        LocalDateTime localDateTime = null;
        JsonObject jsonObjectStartTime = jsonObject.get("startTime").getAsJsonObject();
        JsonElement jsonElementLocalDateTimeValue = jsonObjectStartTime.get("value");
        if (jsonElementLocalDateTimeValue!= null) {
            JsonObject jsonElementLocalDateTime = jsonElementLocalDateTimeValue.getAsJsonObject();
            JsonElement jsonLocalDateDate = jsonElementLocalDateTime.get("date");
            JsonObject jsonLocalDate = jsonLocalDateDate.getAsJsonObject();
            LocalDate localDate = null;
            LocalTime localTime = LocalTime.MIDNIGHT;
            if (jsonLocalDate.has("year")
                    && jsonLocalDate.has("month")
                    && jsonLocalDate.has("day")) {
                localDate = LocalDate.of(
                        jsonLocalDate.get("year").getAsInt(),
                        jsonLocalDate.get("month").getAsInt(),
                        jsonLocalDate.get("day").getAsInt());
            }
            JsonElement jsonLocalTimeTime = jsonElementLocalDateTime.get("time");
            JsonObject jsonLocalTime = jsonLocalTimeTime.getAsJsonObject();

            if (jsonLocalTime.has("hour")
                    && jsonLocalTime.has("minute")) {
                localTime = LocalTime.of(
                        jsonLocalTime.get("hour").getAsInt(),
                        jsonLocalTime.get("minute").getAsInt());
            }
            if (jsonLocalTime.has("hour")
                    && jsonLocalTime.has("minute")
                    && jsonLocalTime.has("second")) {
                localTime = LocalTime.of(
                        jsonLocalTime.get("hour").getAsInt(),
                        jsonLocalTime.get("minute").getAsInt(),
                        jsonLocalTime.get("second").getAsInt());
            }
            if (jsonLocalTime.has("hour")
                    && jsonLocalTime.has("minute")
                    && jsonLocalTime.has("second")
                    && jsonLocalTime.has("nano")) {
                localTime = LocalTime.of(
                        jsonLocalTime.get("hour").getAsInt(),
                        jsonLocalTime.get("minute").getAsInt(),
                        jsonLocalTime.get("second").getAsInt(),
                        jsonLocalTime.get("nano").getAsInt());
            }
            if (localDate != null) {
                localDateTime = LocalDateTime.of(localDate, localTime);
            }
        }
        return localDateTime;
    }

    private EpicTask deserializeSubTasksFromEpic(EpicTask epic, JsonElement json) {
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject.has("subTasksOfEpic")) {
            JsonElement jsonElementSubTasks = jsonObject.get("subTasksOfEpic");
                String s = jsonElementSubTasks.getAsString();
                String[] subTasksIDs = s.split(",");
                try {
                    for (int i = 0; i < subTasksIDs.length; i++) {
                        int id = Integer.parseInt(subTasksIDs[i]);
                        epic.addSubTask((SubTask) new Managers()
                                .getDefault().getTaskById(id));
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (ManagerSaveException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        return epic;
    }
}
