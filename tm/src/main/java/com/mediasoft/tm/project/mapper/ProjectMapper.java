package com.mediasoft.tm.project.mapper;

import com.mediasoft.tm.project.dto.ProjectDto;
import com.mediasoft.tm.project.model.Project;

public interface ProjectMapper {

    ProjectDto toDto(Project project);
}
