package com.mediasoft.tm.task.mapper;

import com.mediasoft.tm.task.dto.TaskDto;
import com.mediasoft.tm.task.model.Task;

public interface TaskMapper {

    TaskDto toDto(Task task);
}
