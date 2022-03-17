package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public interface TaskManagerTest {

    @Test
    void putTaskStandardBehavior() throws IntersectionException, IOException;

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
    void updateTask() throws IntersectionException;

    @Test
    void updateTaskWithWrongId() throws IntersectionException;

    @Test
    void removeAllTasks() throws IntersectionException;

    @Test
    void removeTaskByIdStandardBehavior() throws IntersectionException;

    @Test
    void shouldGetPrioritizedTasks() throws IntersectionException, ManagerSaveException;
}