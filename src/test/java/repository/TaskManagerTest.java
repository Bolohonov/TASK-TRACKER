package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public interface TaskManagerTest {

    @Test
    void putTaskStandardBehavior() throws IntersectionException, IOException, ManagerSaveException;

    @Test
    void putTaskIntersectionException() throws IntersectionException, ManagerSaveException;

    @Test
    void getTaskByIdStandardBehavior() throws IntersectionException, ManagerSaveException;

    @Test
    void getTaskByIdEmptyRepository() throws IntersectionException, ManagerSaveException;

    @Test
    void getTaskByIdWrongId() throws IntersectionException, ManagerSaveException;

    @Test
    void getSingleTasksStandardBehavior() throws IntersectionException, ManagerSaveException;

    @Test
    void getSingleTasksEmptyRepository();

    @Test
    void getEpicTasksStandardBehavior() throws IntersectionException;

    @Test
    void getEpicTasksEmptyRepository();

    @Test
    void getSubTasksByEpicStandardBehavior() throws IntersectionException;

    @Test
    void getSubTasksByEpicEmptyRepository() throws IntersectionException, ManagerSaveException;

    @Test
    void updateTask() throws IntersectionException, ManagerSaveException;

    @Test
    void updateTaskWithWrongId() throws IntersectionException;

    @Test
    void removeAllTasks() throws IntersectionException, ManagerSaveException;

    @Test
    void removeTaskByIdStandardBehavior() throws IntersectionException, ManagerSaveException;

    @Test
    void shouldGetPrioritizedTasks() throws IntersectionException, ManagerSaveException;
}