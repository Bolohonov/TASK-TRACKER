package repository;

import org.junit.jupiter.api.Test;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.Task;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public interface TaskManagerTest {

    @Test
    void putTaskStandardBehavior();

    @Test
    void getTaskById();

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
    void shouldGetPrioritizedTasks();
}