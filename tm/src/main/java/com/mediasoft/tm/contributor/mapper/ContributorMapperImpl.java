package com.mediasoft.tm.contributor.mapper;

import com.mediasoft.tm.contributor.dto.ContributorDto;
import com.mediasoft.tm.contributor.model.Contributor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ContributorMapperImpl implements ContributorMapper {

    @Override
    public ContributorDto toDto(Contributor contributor) {
        return Objects.isNull(contributor) ? null :
                ContributorDto.builder()
                        .id(contributor.getId())
                        .role(contributor.getRole())
                        .build();
    }

    @Override
    public List<ContributorDto> toDto(List<Contributor> contributors) {
        if (Objects.isNull(contributors)) {
            return null;
        }
        return contributors.isEmpty() ? Collections.emptyList() :
                contributors.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
    }
}
