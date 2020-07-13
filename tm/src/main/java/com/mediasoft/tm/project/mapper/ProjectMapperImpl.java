package com.mediasoft.tm.project.mapper;

import com.mediasoft.tm.contributor.mapper.ContributorMapper;
import com.mediasoft.tm.project.dto.ProjectDto;
import com.mediasoft.tm.project.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProjectMapperImpl implements ProjectMapper {

    private final ContributorMapper contributorMapper;

    @Autowired
    public ProjectMapperImpl(ContributorMapper contributorMapper) {
        this.contributorMapper = contributorMapper;
    }

    @Override
    public ProjectDto toDto(Project project) {
        return Objects.isNull(project) ? null :
                ProjectDto.builder()
                        .id(project.getId())
                        .title(project.getTitle())
                        .contributors(
                                contributorMapper.toDto(project.getContributors())
                        ).build();
    }
}
