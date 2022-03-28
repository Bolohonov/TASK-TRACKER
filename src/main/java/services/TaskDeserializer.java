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

public class TaskDeserializer implements JsonDeserializer<Task> {

    @Override
    public Task deserialize(JsonElement json, Type type,
                            JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement jsonElementDurationValue = jsonObject.get("duration");
        JsonObject jsonObjectDuration = jsonElementDurationValue.getAsJsonObject();

        Duration durationOfDays = Duration
                .ofDays(jsonObjectDuration.get("days").getAsLong());
        Duration durationOfHours = Duration
                .ofHours(jsonObjectDuration.get("hours").getAsLong());
        Duration durationOfMinutes = Duration
                .ofMinutes(jsonObjectDuration.get("minutes").getAsLong());
        Duration durationOfSeconds = Duration
                .ofSeconds(jsonObjectDuration.get("seconds").getAsLong());
        Duration durationOfNanos = Duration
                .ofNanos(jsonObjectDuration.get("nanos").getAsLong());
        Duration duration = durationOfDays
                .plus(durationOfHours)
                .plus(durationOfMinutes)
                .plus(durationOfSeconds)
                .plus(durationOfNanos);
        jsonObject = json.getAsJsonObject();
        JsonElement jsonElementLocalDateTime = jsonObject.get("startTime");
        LocalDate localDate = LocalDate.of(
                jsonObject.get("year").getAsInt(),
                jsonObject.get("month").getAsInt(),
                jsonObject.get("day").getAsInt()
        );
        LocalTime localTime = LocalTime.of(
                jsonObject.get("hour").getAsInt(),
                jsonObject.get("minute").getAsInt(),
                jsonObject.get("second").getAsInt(),
                jsonObject.get("nano").getAsInt()
        );
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        jsonObject = json.getAsJsonObject();
        Task task;
        String taskType = jsonObject.get("type").getAsString();
        if (taskType.equals(TaskType.TASK)) {

    }
        switch (taskType) {
            case TaskType.TASK:
                task = new SingleTask(
                        jsonObject.get("name").getAsString(),
                        jsonObject.get("description").getAsString(),
                        jsonObject.get("id").getAsInt(),
                        Optional.of(duration),
                        Optional.of(localDateTime));
                        task.setStatus(TaskStatus.valueOf(jsonObject.get("status")
                                .getAsString()));
                        break;
            case TaskType.EPIC:
                task = new EpicTask(
                        jsonObject.get("name").getAsString(),
                        jsonObject.get("description").getAsString(),
                        jsonObject.get("id").getAsInt();
                break;
            case TaskType.SUBTASK:
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
                break;
        }




        return task;
    }
}
