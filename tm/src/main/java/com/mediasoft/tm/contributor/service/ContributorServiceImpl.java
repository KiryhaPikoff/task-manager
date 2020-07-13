package com.mediasoft.tm.contributor.service;

import com.mediasoft.tm.account.model.Account;
import com.mediasoft.tm.contributor.dto.ContributorDto;
import com.mediasoft.tm.contributor.model.Contributor;
import com.mediasoft.tm.contributor.model.Role;
import com.mediasoft.tm.contributor.repository.ContributorRepository;
import com.mediasoft.tm.project.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContributorServiceImpl implements ContributorService {

    private final ContributorRepository contributorRepository;

    @Autowired
    public ContributorServiceImpl(ContributorRepository contributorRepository) {
        this.contributorRepository = contributorRepository;
    }

    /**
     * Получение контрибутора по его id.
     *
     * @param contributorId
     */
    @Override
    public ContributorDto getById(Long contributorId) {
        var contributor = contributorRepository
                .findById(contributorId)
                .orElseGet(Contributor::new);
        return this.toDto(contributor);
    }

    /**
     * Получение роли, занимаемой аккаунтом на проекте.
     *
     * @param accountId id аккаунта.
     * @param projectId id проекта.
     */
    @Override
    public ContributorDto getByAccountAndProject(Long accountId, Long projectId) {
        var contributor = contributorRepository
                .findByAccountIdAndProjectId(accountId, projectId)
                .orElseGet(Contributor::new);
        return this.toDto(contributor);
    }

    /**
     * Добавление контрибутора в проект.
     *
     * @param accountId id аккаунта, желающего стать контрибутором.
     * @param projectId id проекта, в который добавляется контрибутор.
     * @param role      роль будущего контрибутора на проекте.
     */
    @Override
    public void create(Long accountId, Long projectId, Role role) {
        if (contributorRepository.existsByAccountIdAndProjectId(accountId, projectId)) {
            throw new RuntimeException("Contributor with accountId=" + accountId +
                    "and projectId=" + projectId + " already exists");
        }

        var contributor = Contributor.builder()
                .account(Account.builder().id(accountId).build())
                .project(Project.builder().id(projectId).build())
                .role(role)
                .build();

        contributorRepository.save(contributor);
    }

    /**
     * Смена роли контрибутора на проекте.
     *
     * @param contributorId id контрибутора, которому хотим поменять роль.
     * @param role          новая роль.
     */
    @Override
    public void changeRole(Long contributorId, Role role) {
        if (!contributorRepository.existsById(contributorId)) {
            throw new RuntimeException("Contributor with id=" + contributorId +
                    " not exists");
        }

        var contributor = contributorRepository.getOne(contributorId);
        contributor.setRole(role);
        contributorRepository.save(contributor);
    }

    private ContributorDto toDto(Contributor contributor) {
        return new ContributorDto(
                contributor.getId(),
                contributor.getRole()
        );
    }
}
