package service;

import repository.*;

public class Managers {


    //private static Managers instance;
    private final TaskManager manager = new InMemoryTasksManager();
    private final TaskCreator factory = new TaskFactory();
    private final Printable printer = new TaskPrinter();
    private final TaskUpdater updater = new InMemoryTaskUpdater();

//    public static Managers getInstance() {
//        if (instance == null) {
//            return instance = new Managers();
//        } else {
//            return null;
//        }
//    }

    public TaskManager getDefault() {
        return manager;
    }

    public TaskCreator getFactory() {
        return factory;
    }

    public Printable getPrinter() {
        return printer;
    }

    public TaskUpdater getUpdate() {
        return updater;
    }
}
