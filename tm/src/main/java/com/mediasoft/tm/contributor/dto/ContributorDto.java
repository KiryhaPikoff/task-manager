package com.mediasoft.tm.contributor.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mediasoft.tm.contributor.model.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public class ContributorDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @NotNull
    private final Role role;
}
