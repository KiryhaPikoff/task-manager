package com.mediasoft.tm.task.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mediasoft.tm.task.model.stmach.TaskState;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Builder
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public class TaskDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @NotNull
    private final String title;

    @NotNull
    private final String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final TaskState state;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Date startDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Date finishDate;
}
