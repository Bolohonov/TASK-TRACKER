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
//        if(task.getDuration().isPresent()) {
//            JsonObject jsonObject = jsonElement.getAsJsonObject();
//            JsonObject jsonElementDuration = jsonObject.get("duration").getAsJsonObject();
//            JsonElement jsonElementDurationValue = jsonElementDuration.get("value");
//            JsonObject jsonObjectDuration = jsonElementDurationValue.getAsJsonObject();
//            if(jsonObjectDuration.has("days")) {
//                jsonObjectDuration.addProperty("days", task.getDuration().toString());
//            }
//            if(jsonObjectDuration.has("hours")) {
//                duration.plus(Duration
//                        .ofHours(jsonObjectDuration.get("hours").getAsLong()));
//            }
//            if(jsonObjectDuration.has("minutes")) {
//                duration.plus(Duration
//                        .ofMinutes(jsonObjectDuration.get("minutes").getAsLong()));
//            }
//            if(jsonObjectDuration.has("seconds")) {
//                duration.plus(Duration
//                        .ofSeconds(jsonObjectDuration.get("seconds").getAsLong()));
//            }
//            if(jsonObjectDuration.has("nanos")) {
//                duration.plus(Duration
//                        .ofNanos(jsonObjectDuration.get("nanos").getAsLong()));
//            }
//       }
        return jsonElement;
    }

    @Override
    public Task deserialize(JsonElement json, Type type,
                            JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject jsonElementDuration = jsonObject.get("duration").getAsJsonObject();
        JsonElement jsonElementDurationValue = jsonElementDuration.get("value");
        JsonObject jsonObjectDuration = jsonElementDurationValue.getAsJsonObject();
//        if(jsonObjectDuration.has("days")) {
//            duration = duration.plus(Duration
//                    .ofDays(jsonObjectDuration.get("days").getAsLong()));
//        }
//        if(jsonObjectDuration.has("hours")) {
//            duration.plus(Duration
//                    .ofHours(jsonObjectDuration.get("hours").getAsLong()));
//        }
//        if(jsonObjectDuration.has("minutes")) {
//            duration.plus(Duration
//                    .ofMinutes(jsonObjectDuration.get("minutes").getAsLong()));
//        }
        Duration duration = Duration.ZERO;
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
        JsonObject jsonObjectStartTime = jsonObject.get("startTime").getAsJsonObject();
        JsonElement jsonElementLocalDateTimeValue = jsonObjectStartTime .get("value");
        JsonObject jsonElementLocalDateTime = jsonElementLocalDateTimeValue.getAsJsonObject();
        JsonElement jsonLocalDateDate = jsonElementLocalDateTime.get("date");
        JsonObject jsonLocalDate = jsonLocalDateDate.getAsJsonObject();
        LocalDate localDate = null;
        LocalTime localTime = LocalTime.MIDNIGHT;
        if(jsonLocalDate.has("year")
                && jsonLocalDate.has("month")
                && jsonLocalDate.has("day") ) {
            localDate = LocalDate.of(
                    jsonLocalDate.get("year").getAsInt(),
                    jsonLocalDate.get("month").getAsInt(),
                    jsonLocalDate.get("day").getAsInt());
        }
        JsonElement jsonLocalTimeTime = jsonElementLocalDateTime.get("time");
        JsonObject jsonLocalTime = jsonLocalTimeTime.getAsJsonObject();

        if(jsonLocalTime.has("hour")
                && jsonLocalTime.has("minute")) {
             localTime = LocalTime.of(
                     jsonLocalTime.get("hour").getAsInt(),
                     jsonLocalTime.get("minute").getAsInt());
        }
        if(jsonLocalTime.has("hour")
                && jsonLocalTime.has("minute")
        && jsonLocalTime.has("second")) {
            localTime = LocalTime.of(
                    jsonLocalTime.get("hour").getAsInt(),
                    jsonLocalTime.get("minute").getAsInt(),
                    jsonLocalTime.get("second").getAsInt());
        }
        if(jsonLocalTime.has("hour")
                && jsonLocalTime.has("minute")
                && jsonLocalTime.has("second")
                && jsonLocalTime.has("nano")) {
            localTime = LocalTime.of(
                    jsonLocalTime.get("hour").getAsInt(),
                    jsonLocalTime.get("minute").getAsInt(),
                    jsonLocalTime.get("second").getAsInt(),
                    jsonLocalTime.get("nano").getAsInt());
        }
        LocalDateTime localDateTime = null;
        if(localDate != null) {
            localDateTime = LocalDateTime.of(localDate, localTime);
        } 
        jsonObject = json.getAsJsonObject();
        Task task = null;
        Optional<Duration> durationOrZero;
        if(duration.isZero()) {
            durationOrZero = Optional.empty();
        } else {
            durationOrZero = Optional.of(duration);
        }
        Optional<LocalDateTime> timeOrZero;
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
}
