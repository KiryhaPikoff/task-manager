package com.mediasoft.tm.config.security.aurhorization;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mediasoft.tm.contributor.model.Role;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Builder
public class ContributionGrantedAuthority implements GrantedAuthority {

    private Long projectId;

    private Role role;

    @JsonCreator
    public ContributionGrantedAuthority(
            @JsonProperty("projectId") Long projectId,
            @JsonProperty("role") Role role) {
        this.projectId = projectId;
        this.role = role;
    }

    @Override
    @JsonIgnore
    public String getAuthority() {
        return projectId + role.name();
    }
}
