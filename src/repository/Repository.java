package repository;

import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Repository {

    private static HashMap<Long id, ArrayList<SingleTask>> singleTasks = new HashMap<>();
    private static ArrayList<EpicTask> epicTasks = new LinkedList<>();
    private static LinkedList<SubTask> subTasks = new LinkedList<>();
}
