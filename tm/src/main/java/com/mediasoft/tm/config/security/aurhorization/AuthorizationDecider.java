package com.mediasoft.tm.config.security.aurhorization;

import com.mediasoft.tm.account.service.AccountService;
import com.mediasoft.tm.contributor.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("authDecider")
public class AuthorizationDecider {

    private final AccountService accountService;

    @Autowired
    public AuthorizationDecider(AccountService accountService) {
        this.accountService = accountService;
    }

    public boolean canGetAccount(Authentication auth, Long accountId) {
        var account = accountService.getById(accountId);
        String currentUserEmail = auth.getPrincipal().toString();
        return (currentUserEmail.equals(account.getEmail()));
    }

    public boolean canSeeProject(Authentication auth, Long projectId) {
        var projectRole = this.getRoleOnProject(auth, projectId);
        return Objects.nonNull(projectRole);
    }

    public boolean canUpdateProject(Authentication auth, Long projectId) {
        var projectRole = this.getRoleOnProject(auth, projectId);
        return projectRole.equals(Role.OWNER);
    }

    public boolean canCreateTask(Authentication auth, Long projectId) {
        var projectRole = this.getRoleOnProject(auth, projectId);
        return projectRole.equals(Role.MANAGER) ||
               projectRole.equals(Role.OWNER);
    }

    public boolean canUpdateTask(Authentication auth, Long projectId) {
        var projectRole = this.getRoleOnProject(auth, projectId);
        return projectRole.equals(Role.MANAGER) ||
                projectRole.equals(Role.OWNER);
    }

    public boolean canTakeTaskInProcess(Authentication auth, Long projectId) {
        var projectRole = this.getRoleOnProject(auth, projectId);
        return projectRole.equals(Role.MANAGER) ||
               projectRole.equals(Role.OWNER);
    }

    public boolean canSendTaskToCheck(Authentication auth, Long projectId) {
        var projectRole = this.getRoleOnProject(auth, projectId);
        return projectRole.equals(Role.DEVELOPER) ||
               projectRole.equals(Role.OWNER);
    }

    public boolean canApproveTask(Authentication auth, Long projectId) {
        var projectRole = this.getRoleOnProject(auth, projectId);
        return projectRole.equals(Role.QA) ||
               projectRole.equals(Role.OWNER);
    }

    public boolean canDispproveTask(Authentication auth, Long projectId) {
        var projectRole = this.getRoleOnProject(auth, projectId);
        return projectRole.equals(Role.QA) ||
               projectRole.equals(Role.OWNER);
    }

    public boolean canAttachContributorToProject(Authentication auth, Long projectId) {
        var projectRole = this.getRoleOnProject(auth, projectId);
        return projectRole.equals(Role.MANAGER) ||
               projectRole.equals(Role.OWNER);
    }

    public boolean canChangeContributorRole(Authentication auth, Long projectId) {
        var projectRole = this.getRoleOnProject(auth, projectId);
        return projectRole.equals(Role.OWNER);
    }

    public boolean canAttachContributorToTask(Authentication auth, Long projectId) {
        var projectRole = this.getRoleOnProject(auth, projectId);
        return projectRole.equals(Role.MANAGER) ||
               projectRole.equals(Role.OWNER);
    }

    public boolean canDetachContributorFromTask(Authentication auth, Long projectId) {
        var projectRole = this.getRoleOnProject(auth, projectId);
        return projectRole.equals(Role.MANAGER) ||
               projectRole.equals(Role.OWNER);
    }


    private Role getRoleOnProject(Authentication auth, Long projectId) {
        var authorities = auth.getAuthorities();
        var projectAuthority = authorities.stream()
                .map(grantedAuthority -> (ContributionGrantedAuthority) grantedAuthority)
                .filter(grantedAuthority -> grantedAuthority.getProjectId().equals(projectId))
                .findFirst();
        if (projectAuthority.isEmpty()) {
            throw new RuntimeException("You have not access to project with id=" + projectId);
        }
        return projectAuthority.get().getRole();
    }
}