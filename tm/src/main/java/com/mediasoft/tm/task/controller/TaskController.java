package com.mediasoft.tm.task.controller;

import com.mediasoft.tm.account.service.AccountService;
import com.mediasoft.tm.task.dto.TaskDto;
import com.mediasoft.tm.task.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class TaskController {

    private final AccountService accountService;

    private final TaskService taskService;

    @GetMapping
    @PreAuthorize("@authDecider.canSeeProject(authentication, #projectId)")
    public ResponseEntity<List<TaskDto>> getProjectTasks(Authentication auth,
                                                         @PathVariable Long projectId) {
        var tasks = taskService.getByProjectId(projectId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("@authDecider.canCreateTask(authentication, #projectId)")
    public ResponseEntity create(Authentication auth,
                                 @PathVariable Long projectId,
                                 @RequestBody @Valid TaskDto taskDto) {
        var creatorAccount = accountService.getByEmail(auth.getPrincipal().toString());
        taskService.create(
                creatorAccount.getId(),
                projectId,
                taskDto
        );
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("@authDecider.canUpdateTask(authentication, #projectId)")
    public ResponseEntity update(@PathVariable Long projectId,
                                 @PathVariable Long taskId,
                                 @RequestBody @Valid TaskDto taskDto) {
        taskService.update(taskId, taskDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{taskId}/takeinprocess")
    @PreAuthorize("@authDecider.canTakeTaskInProcess(authentication, #projectId)")
    public ResponseEntity takeToProcess(@PathVariable Long projectId,
                                        @PathVariable Long taskId) {
        taskService.takeInProcess(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{taskId}/sendtocheck")
    @PreAuthorize("@authDecider.canSendTaskToCheck(authentication, #projectId)")
    public ResponseEntity sendToCheck(@PathVariable Long projectId,
                                      @PathVariable Long taskId) {
        taskService.sendToCheck(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{taskId}/approve")
    @PreAuthorize("@authDecider.canApproveTask(authentication, #projectId)")
    public ResponseEntity approve(@PathVariable Long projectId,
                                  @PathVariable Long taskId) {
        taskService.approve(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{taskId}/disapprove")
    @PreAuthorize("@authDecider.canDispproveTask(authentication, #projectId)")
    public ResponseEntity disapprove(@PathVariable Long projectId,
                                     @PathVariable Long taskId) {
        taskService.disapprove(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{taskId}/attachcontributor")
    @PreAuthorize("@authDecider.canAttachContributorToTask(authentication, #projectId)")
    public ResponseEntity attachContributor(@PathVariable Long projectId,
                                            @RequestParam Long contributorId,
                                            @PathVariable Long taskId) {
        taskService.attachContributor(contributorId, taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{taskId}/detachcontributor")
    @PreAuthorize("@authDecider.canDetachContributorFromTask(authentication, #projectId)")
    public ResponseEntity detachContributor(@PathVariable Long projectId,
                                            @RequestParam Long contributorId,
                                            @PathVariable Long taskId) {
        taskService.detachContributor(contributorId, taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
