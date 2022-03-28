package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.reflections.Reflections;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;
import tasks.Task;

import java.util.Set;

public class ConfigTaskJsonAdapter {

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(SingleTask.class, new TaskJsonAdapter())
                .registerTypeAdapter(EpicTask.class, new TaskJsonAdapter())
                .registerTypeAdapter(SubTask.class, new TaskJsonAdapter());
        return gsonBuilder;
    }
}
