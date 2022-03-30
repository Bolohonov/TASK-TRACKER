package services;

import com.google.gson.GsonBuilder;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

public class ConfigTaskJsonAdapter {

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(SingleTask.class, new TaskJsonAdapter())
                .registerTypeAdapter(EpicTask.class, new TaskJsonAdapter())
                .registerTypeAdapter(SubTask.class, new TaskJsonAdapter())
                .registerTypeAdapter(Task.class, new TaskJsonAdapter());
        return gsonBuilder;
    }
}
