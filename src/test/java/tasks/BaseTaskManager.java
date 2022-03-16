package tasks;

import org.junit.jupiter.api.Test;
import repository.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseTaskManager {

    protected static final Managers managers = new Managers();
    protected static final TaskCreator creator = managers.getFactory();
    protected static final TaskManager manager = managers.getTaskManager();

    protected EpicTask createEpicTask() {
        return creator.createEpicTask(new String[]{"TestEpicName", "TestEpicDescription"});
    }

    protected SingleTask createSingleTask() throws IntersectionException {
        return creator.createSingleTask(new String[]{"TestEpicName", "TestEpicDescription"},
                Duration.ofHours(1), LocalDateTime.now(ZoneId.of("Europe/Moscow")).plusHours(2));
    }

    EpicTask epicTask1 = new EpicTask("TestEpicName",
            "TestEpicDescription", 1001);
    EpicTask epicTask2 = new EpicTask("TestEpicName2",
            "TestEpicDescription2", 1002);
    EpicTask epicTask3 = new EpicTask("TestEpicName3",
            "TestEpicDescription3", 1003);
    SubTask subTask1 = new SubTask(epicTask1, "TestNameSub1",
            "TestDescriptionSub1", 1004, Optional.of(Duration.ofHours(1)),
            Optional.of(LocalDateTime
                    .of(2022, 01, 10, 1, 00, 10)));
    SubTask subTask2 = new SubTask(epicTask1, "TestNameSub1",
            "TestDescriptionSub1", 1005, Optional.of(Duration.ofHours(2)),
            Optional.of(LocalDateTime
                    .of(2022, 01, 10, 3, 00, 10)));
    SubTask subTask3 = new SubTask(epicTask2, "TestNameSub1",
            "TestDescriptionSub1", 1006, Optional.of(Duration.ofHours(2)),
            Optional.of(LocalDateTime
                    .of(2022, 01, 10, 8, 00, 10)));
    SingleTask task1 = new SingleTask("TestSingleName",
            "TestSingleDescription", 1007, Optional.of(Duration.ofHours(2)),
            Optional.of(LocalDateTime
                    .of(2022, 01, 13, 7, 00, 10)));


    @Test
    void getName() {
        assertEquals("TestEpicName", epicTask1.getName());
        assertEquals("TestSingleName", task1.getName());
        assertEquals("TestNameSub1", subTask1.getName());
    }

    @Test
    void setName() {
        EpicTask epicTask1 = new EpicTask("TestEpicName",
                "TestEpicDescription", 1001);
        SubTask subTask1 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1004, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2022, 01, 10, 1, 00, 10)));
        SubTask subTask2 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1005, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 01, 10, 3, 00, 10)));
        SingleTask task1 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1007, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 01, 13, 7, 00, 10)));
        epicTask1.setName("NewTestEpicName");
        task1.setName("NewTestSingleName");
        subTask1.setName("NewTestNameSub1");
        assertEquals("NewTestEpicName", epicTask1.getName());
        assertEquals("NewTestSingleName", task1.getName());
        assertEquals("NewTestNameSub1", subTask1.getName());
    }

    @Test
    void setDescription() {
        EpicTask epicTask1 = new EpicTask("TestEpicName",
                "TestEpicDescription", 1001);
        SubTask subTask1 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1004, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2022, 01, 10, 1, 00, 10)));
        SubTask subTask2 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1005, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 01, 10, 3, 00, 10)));
        SingleTask task1 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1007, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 01, 13, 7, 00, 10)));
        epicTask1.setDescription("NewTestEpicName");
        task1.setDescription("NewTestSingleName");
        subTask1.setDescription("NewTestNameSub1");
        assertEquals("NewTestEpicName", epicTask1.getDescription());
        assertEquals("NewTestSingleName", task1.getDescription());
        assertEquals("NewTestNameSub1", subTask1.getDescription());
    }

    @Test
    void getDescription() {
        assertEquals("TestEpicDescription", epicTask1.getDescription());
        assertEquals("TestSingleDescription", task1.getDescription());
        assertEquals("TestDescriptionSub1", subTask1.getDescription());
    }

    @Test
    void getId() {
        assertEquals(1001, epicTask1.getId());
        assertEquals(1007, task1.getId());
        assertEquals(1004, subTask1.getId());
    }

    @Test
    void getStatus() {
        assertEquals(TaskStatus.NEW, epicTask2.getStatus());
        assertEquals(TaskStatus.NEW, task1.getStatus());
        assertEquals(TaskStatus.NEW, subTask1.getStatus());
    }

    @Test
    void setStatus() {
        EpicTask epicTask1 = new EpicTask("TestEpicName",
                "TestEpicDescription", 1001);
        SubTask subTask1 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1004, Optional.of(Duration.ofHours(1)),
                Optional.of(LocalDateTime
                        .of(2022, 01, 10, 1, 00, 10)));
        SubTask subTask2 = new SubTask(epicTask1, "TestNameSub1",
                "TestDescriptionSub1", 1005, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 01, 10, 3, 00, 10)));
        SingleTask task1 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1007, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 01, 13, 7, 00, 10)));
        epicTask1.setStatus(TaskStatus.DONE);
        subTask1.setStatus(TaskStatus.DONE);
        task1.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.IN_PROGRESS, epicTask1.getStatus());
        assertEquals(TaskStatus.DONE, subTask1.getStatus());
        assertEquals(TaskStatus.DONE, task1.getStatus());
    }

    @Test
    void getType() {
        assertEquals(TaskType.EPIC, epicTask1.getType());
        assertEquals(TaskType.SUBTASK, subTask1.getType());
        assertEquals(TaskType.TASK, task1.getType());
    }

    @Test
    void testToString() {
        SingleTask task2 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1008, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 01, 13, 10, 00, 10)));
        String expString = "Задача{" +
                "Имя='" + "TestSingleName" + '\'' +
                ", Описание='" + "TestSingleDescription" + '\'' +
                ", ID=" + 1008 +
                ", Статус=" + TaskStatus.NEW +
                ", Продолжительность=" + "PT2H" +
                ", Время начала=" + "2022-01-13T10:00:10" +
                '}';
        assertEquals(expString, task2.toString());
    }

    @Test
    void testToStringWithTaskParameter() {
        SingleTask task2 = new SingleTask("TestSingleName",
                "TestSingleDescription", 1008, Optional.of(Duration.ofHours(2)),
                Optional.of(LocalDateTime
                        .of(2022, 01, 13, 10, 00, 10)));
        String expString = 1008 + "," + TaskType.TASK + "," + "TestSingleName" + "," + TaskStatus.NEW
                + "," + "TestSingleDescription" + "," + "PT2H" + "," + "2022-01-13T10:00:10" + ",";
        assertEquals(expString, task2.toString(task2));
    }

    @Test
    void getDuration() {
        assertEquals(Optional.of(Duration.ofHours(3)), epicTask1.getDuration());
        assertEquals(Optional.of(Duration.ofHours(2)), task1.getDuration());
        assertEquals(Optional.of(Duration.ofHours(1)), subTask1.getDuration());
    }

//    @Test
//    void setDuration() {
//        EpicTask epicTask1 = new EpicTask("TestEpicName",
//                "TestEpicDescription", 1001);
//        SubTask subTask1 = new SubTask(epicTask1, "TestNameSub1",
//                "TestDescriptionSub1", 1004, Optional.of(Duration.ofHours(1)),
//                Optional.of(LocalDateTime
//                        .of(2022, 01, 10, 1, 00, 10)));
//        SubTask subTask2 = new SubTask(epicTask1, "TestNameSub1",
//                "TestDescriptionSub1", 1005, Optional.of(Duration.ofHours(2)),
//                Optional.of(LocalDateTime
//                        .of(2022, 01, 10, 3, 00, 10)));
//        SingleTask task1 = new SingleTask("TestSingleName",
//                "TestSingleDescription", 1007, Optional.of(Duration.ofHours(2)),
//                Optional.of(LocalDateTime
//                        .of(2022, 01, 13, 7, 00, 10)));
//        epicTask1.setStatus(TaskStatus.DONE);
//        subTask1.setStatus(TaskStatus.DONE);
//        task1.setStatus(TaskStatus.DONE);
//        assertEquals(TaskStatus.IN_PROGRESS, epicTask1.getStatus());
//        assertEquals(TaskStatus.DONE, subTask1.getStatus());
//        assertEquals(TaskStatus.DONE, task1.getStatus());
//    }

    @Test
    void getStartTime() {
    }
}
