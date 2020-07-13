package com.mediasoft.tm.project.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mediasoft.tm.contributor.dto.ContributorDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public class ProjectDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @NotBlank
    private final String title;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final List<ContributorDto> contributors;
}
