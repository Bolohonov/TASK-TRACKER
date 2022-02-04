package dto;

import entity.TaskStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskDto {

    private String name;
    private String description;
    private Integer id;
    private TaskStatus status;
}
