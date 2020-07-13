package com.mediasoft.tm.task.service;

import com.mediasoft.tm.task.dto.TaskDto;

import java.util.List;

public interface TaskService {

    List<TaskDto> getByProjectId(Long projectId);

    /**
     * Добавление задачи на проект.
     * @param creatorAccountId id аккаунта, который создает задачу.
     * @param projectId id проекта, в который добавляется задача.
     * @param taskDto задача.
     */
    void create(Long creatorAccountId, Long projectId, TaskDto taskDto);

    /**
     * Перевод задачи на выполнение.
     * @param taskId
     */
    void takeInProcess(Long taskId);

    /**
     * Отправить задачу на проверку.
     * @param taskId
     */
    void sendToCheck(Long taskId);

    /**
     * Принять задачу.
     * @param taskId
     */
    void approve(Long taskId);

    /**
     * Не принять задачу.
     * @param taskId
     */
    void disapprove(Long taskId);

    /**
     * Изменение задачи.
     * @param taskDto новое состояние задачи.
     */
    void update(Long taskId, TaskDto taskDto);

    /**
     * Прикрепление контрибутора к задаче.
     * @param contributorId id контрибутора.
     * @param taskId id задачи, к которой контрибутор прикрепляется.
     */
    void attachContributor(Long contributorId, Long taskId);

    /**
     * Открепление контрибутора от задачи.
     * @param contributorId id контрибутора.
     * @param taskId id задачи, от которой контрибутор открепляется.
     */
    void detachContributor(Long contributorId, Long taskId);
}
