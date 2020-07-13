package com.mediasoft.tm.contributor.service;

import com.mediasoft.tm.contributor.dto.ContributorDto;
import com.mediasoft.tm.contributor.model.Role;

public interface ContributorService {

    /**
     * Получение контрибутора по его id.
     * @param contributorId
     */
    ContributorDto getById(Long contributorId);

    /**
     * Получение роли, занимаемой аккаунтом на проекте.
     * @param accountId id аккаунта.
     * @param projectId id проекта.
     */
    ContributorDto getByAccountAndProject(Long accountId, Long projectId);

    /**
     * Добавление контрибутора в проект.
     * @param accountId id аккаунта, желающего стать контрибутором.
     * @param projectId id проекта, в который добавляется контрибутор.
     * @param role роль будущего контрибутора на проекте.
     */
    void create(Long accountId, Long projectId, Role role);

    /**
     * Смена роли контрибутора на проекте.
     * @param contributorId id контрибутора, которому хотим поменять роль.
     * @param role новая роль.
     */
    void changeRole(Long contributorId, Role role);
}
