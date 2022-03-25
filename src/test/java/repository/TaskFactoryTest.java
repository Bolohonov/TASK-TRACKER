package repository;

import org.junit.jupiter.api.Test;
import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;

import java.net.MalformedURLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TaskFactoryTest implements TaskCreatorTest{

    protected static Managers managers;

    static {
        try {
            managers = new Managers();
        } catch (ManagerSaveException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    protected static final TaskCreator creator = managers.getFactory();

    @Test
    @Override
    public void createSingleTaskStandardBehavior() throws IntersectionException {
        SingleTask single1 = creator.createSingleTask(new String[]{"TestSingleName", "TestSingleDescription"},
                Duration.ofHours(2),
                LocalDateTime
                        .of(2022, 02, 13, 7, 00, 10));
        SingleTask single2 = new SingleTask("TestSingleName", "TestSingleDescription", single1.getId(),
                Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 02, 13, 7, 00, 10)));
        assertEquals(single1, single2);
    }

    @Test
    @Override
    public void createEpicTaskStandardBehavior() {
        EpicTask epicTask1 = creator.createEpicTask(new String[]{"TestEpicName","TestEpicDescription"});
        EpicTask epicTask2 = new EpicTask("TestEpicName",
                "TestEpicDescription", epicTask1.getId());
        assertEquals(epicTask1, epicTask2);
    }

    @Test
    @Override
    public void createSubTaskStandardBehavior() throws IntersectionException {
        EpicTask epicTask1 = creator.createEpicTask(new String[]{"TestEpicName","TestEpicDescription"});
        SubTask subTask1 = creator.createSubTask(epicTask1, new String[]{"TestNameSub1","TestDescriptionSub1"},
                Duration.ofHours(1), LocalDateTime.of(2022, 02, 10, 1, 00, 10));
        SubTask subTask2 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", subTask1.getId(), Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2022, 02, 10, 1, 00, 10)));
        assertEquals(subTask1, subTask2);
    }

    @Test
    @Override
    public void createSubTaskWithEpicNull() throws IntersectionException {
        SubTask subTask1 = creator.createSubTask(null, new String[]{"TestNameSub1","TestDescriptionSub1"},
                Duration.ofHours(1), LocalDateTime.of(2022, 02, 10, 1, 00, 10));
        assertEquals(null, subTask1);
    }
}