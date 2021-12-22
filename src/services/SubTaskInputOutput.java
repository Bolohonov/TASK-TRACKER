package services;

import storage.TaskStatus;
import tasks.SubTask;
import tasks.Task;

public class SubTaskInputOutput {

    public static SubTask saveSubTask(Task task) {
        String[] userTask = TaskInputOutput.saveUserTask();
        int hash = TaskInputOutput.hashCode(userTask[0], userTask[1]);
        SubTask subTask = new SubTask(task, userTask[0], userTask[1], hash,
                TaskStatus.NEW);
        return subTask;
    }

    public static SubTask saveSubTaskFromUserSelect() {
        Task task = TaskInputOutput.selectUserTaskByID();
        if (task != null) {
            return saveSubTask(task);
        } else {
            return null;
        }
    }
}
