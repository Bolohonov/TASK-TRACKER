package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.reflections.Reflections;
import tasks.Task;

import java.util.Set;

public class ConfigTaskJsonAdapter {

    public static GsonBuilder getGsonBuilder() {
        Reflections reflections = new Reflections(Task.class);
        Set<Class<? extends Task>> classes = reflections.getSubTypesOf(Task.class);
        GsonBuilder gsonBuilder = new GsonBuilder();
        for (Class aClass : classes) {
            gsonBuilder.registerTypeAdapter(aClass, new TaskJsonAdapter());
        }
        return gsonBuilder;
    }
}
