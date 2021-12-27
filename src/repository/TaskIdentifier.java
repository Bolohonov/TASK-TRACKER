package repository;

import tasks.EpicTask;
import tasks.SingleTask;
import tasks.SubTask;

public class TaskIdentifier {

    private boolean checkIdNumber(int id) {
        boolean isIDAlreadyExist = false;
        for (SingleTask singleTask : SingleTaskRepository.getTasks()) {
            if (singleTask.getId() == id) {
                isIDAlreadyExist = true;
            }
        }
        for (EpicTask epicTask : EpicTaskRepository.getTasks()) {
            if (epicTask.getId() == id) {
                isIDAlreadyExist = true;
            }
        }
        try {
            for (SubTask subtask : EpicTaskRepository.getSubTasks()) {
                if (subtask.getId() == id) {
                    isIDAlreadyExist = true;
                }
            }
        } catch (NullPointerException exp) {
        }
        return isIDAlreadyExist;
    }

    public int getId() {
        int id = (int) (Math.random() * 17 + Math.random() * 137);
        if ((!checkIdNumber(id)) && (id != 0)) {
            return id;
        } else {
            return getId();
        }
    }
}
