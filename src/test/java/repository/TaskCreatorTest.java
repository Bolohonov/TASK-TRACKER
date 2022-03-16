package repository;

public interface TaskCreatorTest {

    void createSingleTaskStandardBehavior() throws IntersectionException;

    void createEpicTaskStandardBehavior();

    void createSubTaskStandardBehavior() throws IntersectionException;

    void createSubTaskWithEpicNull() throws IntersectionException;
}
