package com.mediasoft.tm.project.service;

import com.mediasoft.tm.contributor.model.Role;
import com.mediasoft.tm.contributor.service.ContributorService;
import com.mediasoft.tm.project.dto.ProjectDto;
import com.mediasoft.tm.project.mapper.ProjectMapper;
import com.mediasoft.tm.project.model.Project;
import com.mediasoft.tm.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ContributorService contributorService;

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectServiceImpl(ContributorService contributorService,
                              ProjectRepository projectRepository,
                              ProjectMapper projectMapper) {
        this.contributorService = contributorService;
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    /**
     * Получение проекта по его id.
     *
     * @param projectId
     */
    @Override
    public ProjectDto getById(Long projectId) {
        var project = projectRepository
                .findById(projectId)
                .orElseGet(Project::new);
        return projectMapper.toDto(project);
    }

    /**
     * Создание проекта.
     *
     * @param projectDto
     */
    @Override
    @Transactional
    public void create(Long creatorAccountId, ProjectDto projectDto) {
        var project = this.createNewFrom(projectDto);
        projectRepository.save(project);
        contributorService.create(creatorAccountId, project.getId(), Role.OWNER);
    }

    /**
     * Обновление проекта.
     *
     * @param projectDto
     */
    @Override
    public void update(Long projectId, ProjectDto projectDto) {
        if (!projectRepository.existsById(projectId)) {
            throw new RuntimeException("Project with id=" + projectId +
                    " not exists");
        }

        var project = projectRepository.getOne(projectId);
        project.setTitle(projectDto.getTitle());
        projectRepository.save(project);
    }

    private Project createNewFrom(ProjectDto projectDto) {
        return Project.builder()
                .id(projectDto.getId())
                .title(projectDto.getTitle())
                .build();
    }
}
