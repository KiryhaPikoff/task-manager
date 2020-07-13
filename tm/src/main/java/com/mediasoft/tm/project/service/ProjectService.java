package com.mediasoft.tm.project.service;

import com.mediasoft.tm.project.dto.ProjectDto;

public interface ProjectService {

    /**
     * Получение проекта по его id.
     * @param projectId
     */
    ProjectDto getById(Long projectId);

    /**
     * Создание проекта.
     * @param projectDto
     */
    void create(Long creatorAccountId, ProjectDto projectDto);

    /**
     * Обновление проекта.
     * @param projectDto
     */
    void update(Long projectId, ProjectDto projectDto);
}
