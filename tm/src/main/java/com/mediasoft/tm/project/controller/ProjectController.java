package com.mediasoft.tm.project.controller;

import com.mediasoft.tm.account.service.AccountService;
import com.mediasoft.tm.project.dto.ProjectDto;
import com.mediasoft.tm.project.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/projects")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ProjectController {

    private final AccountService accountService;

    private final ProjectService projectService;

    @GetMapping("/{projectId}")
    @PreAuthorize("@authDecider.canSeeProject(authentication, #projectId)")
    public ResponseEntity<ProjectDto> getById(@PathVariable(name = "projectId") Long projectId) {
        var project = projectService.getById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(Authentication auth,
                                 @RequestBody @Valid ProjectDto projectDto) {
        var creatorAccount = accountService.getByEmail(auth.getPrincipal().toString());
        projectService.create(creatorAccount.getId(), projectDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{projectId}")
    @PreAuthorize("@authDecider.canUpdateProject(authentication, #projectId)")
    public ResponseEntity update(@PathVariable Long projectId,
                                 @RequestBody @Valid ProjectDto projectDto) {
        projectService.update(projectId, projectDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
