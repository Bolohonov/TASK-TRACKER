package repository;

import tasks.EpicTask;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class Repository {

    private HashMap<Long, ArrayList<Task>> tasks;

    public HashMap<Long, ArrayList<Task>> getTasks() {
        return tasks;
    }

}
