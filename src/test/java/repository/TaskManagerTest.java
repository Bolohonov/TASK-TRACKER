package repository;

import org.junit.jupiter.api.Test;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.Task;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public interface TaskManagerTest {

    Managers managers = new Managers();
    TaskManager manager = managers.getTaskManager();
    TaskManager managerToFile = managers.getTaskManagerToFile();
    TaskCreator creator = managers.getFactory();

    @Test
    void putTaskStandardBehavior() throws IntersectionException;

    @Test
    public void getTaskByIdStandardBehavior() throws IntersectionException;

    @Test
    void getSingleTasks();

    @Test
    void getEpicTasks();

    @Test
    void getSubTasksByEpic();

    @Test
    void updateTask();

    @Test
    void removeAllTasks();

    @Test
    void removeTaskById();

    @Test
    void getHistory();

    @Test
    void shouldGetPrioritizedTasks() throws IntersectionException;
}