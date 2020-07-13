package com.mediasoft.tm.contributor.mapper;

import com.mediasoft.tm.contributor.dto.ContributorDto;
import com.mediasoft.tm.contributor.model.Contributor;

import java.util.List;

public interface ContributorMapper {

    ContributorDto toDto(Contributor contributor);

    List<ContributorDto> toDto(List<Contributor> contributors);
}
