package com.mediasoft.tm.task.mapper;

import com.mediasoft.tm.task.dto.TaskDto;
import com.mediasoft.tm.task.model.Task;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDto toDto(Task task) {
        return Objects.isNull(task) ? null :
                TaskDto.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .state(task.getState())
                        .startDate(task.getStartDate())
                        .finishDate(task.getFinishDate())
                        .build();
    }
}
