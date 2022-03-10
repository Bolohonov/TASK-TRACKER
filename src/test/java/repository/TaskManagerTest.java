package repository;

import org.junit.jupiter.api.Test;

public interface TaskManagerTest {

    Managers managers = new Managers();
    TaskManager manager = managers.getTaskManager();
    TaskManager managerToFile = managers.getTaskManagerToFile();
    TaskCreator creator = managers.getFactory();

    @Test
    void putTaskStandardBehavior() throws IntersectionException;

    @Test
    void putTaskIntersectionException() throws IntersectionException;

    @Test
    void getTaskByIdStandardBehavior() throws IntersectionException;

    @Test
    void getTaskByIdEmptyRepository() throws IntersectionException;

    @Test
    void getTaskByIdWrongId() throws IntersectionException;

    @Test
    void getSingleTasksStandardBehavior() throws IntersectionException;

    @Test
    void getSingleTasksEmptyRepository();

    @Test
    void getEpicTasksStandardBehavior() throws IntersectionException;

    @Test
    void getEpicTasksEmptyRepository();

    @Test
    void getSubTasksByEpicStandardBehavior() throws IntersectionException;

    @Test
    void getSubTasksByEpicEmptyRepository() throws IntersectionException;

    @Test
    void updateTask();

    @Test
    void removeAllTasks() throws IntersectionException;

    @Test
    void removeTaskByIdStandardBehavior() throws IntersectionException;

    @Test
    void getHistoryStandardBehavior() throws IntersectionException;

    @Test
    void shouldGetPrioritizedTasks() throws IntersectionException;
}