package com.mediasoft.tm.contributor.controller;

import com.mediasoft.tm.contributor.dto.ContributorDto;
import com.mediasoft.tm.contributor.service.ContributorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/projects/{projectId}/contributors")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ContributorController {

    private final ContributorService contributorService;

    @PostMapping
    @PreAuthorize("@authDecider.canAttachContributorToProject(authentication, #projectId)")
    public ResponseEntity create(Authentication auth,
                                 @PathVariable Long projectId,
                                 @RequestParam Long accountId,
                                 @RequestBody @Valid ContributorDto contributorDto) {
        contributorService.create(
                accountId,
                projectId,
                contributorDto.getRole()
        );
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{contributorId}")
    @PreAuthorize("@authDecider.canChangeContributorRole(authentication, #projectId)")
    public ResponseEntity changeRole(@PathVariable Long projectId,
                                     @PathVariable Long contributorId,
                                     @RequestBody @Valid ContributorDto contributorDto) {
        contributorService.changeRole(contributorId, contributorDto.getRole());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
