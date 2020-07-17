package com.mediasoft.tm.task.service;

import com.mediasoft.tm.contributor.model.Contributor;
import com.mediasoft.tm.contributor.model.Role;
import com.mediasoft.tm.contributor.repository.ContributorRepository;
import com.mediasoft.tm.contributor.service.ContributorService;
import com.mediasoft.tm.task.dto.TaskDto;
import com.mediasoft.tm.task.mapper.TaskMapper;
import com.mediasoft.tm.task.model.Task;
import com.mediasoft.tm.task.model.stmach.TaskEvent;
import com.mediasoft.tm.task.model.stmach.TaskState;
import com.mediasoft.tm.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final ContributorService contributorService;

    private final ContributorRepository contributorRepository;

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final StateMachineFactory<TaskState, TaskEvent> factory;

    @Autowired
    public TaskServiceImpl(ContributorService contributorService,
                           ContributorRepository contributorRepository,
                           TaskRepository taskRepository,
                           TaskMapper taskMapper,
                           StateMachineFactory<TaskState, TaskEvent> factory) {
        this.contributorService = contributorService;
        this.contributorRepository = contributorRepository;
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.factory = factory;
    }

    @Override
    public List<TaskDto> getByProjectId(Long projectId) {
        var c = taskRepository
                .getDistinctByContributors_ProjectId(projectId);
        return c.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Добавление задачи на проект.
     *
     * @param creatorAccountId id аккаунта, который создает задачу.
     * @param projectId        id проекта, в который добавляется задача.
     * @param taskDto          задача.
     */
    @Override
    public void create(Long creatorAccountId, Long projectId, TaskDto taskDto) {
        var contributor = contributorService
                .getByAccountAndProject(creatorAccountId, projectId);

        var task = this.createFrom(taskDto);

        this.addContributor(contributor.getId(), task);
        task.setState(TaskState.NEW);

        taskRepository.save(task);
    }

    /**
     * Перевод задачи на выполнение.
     *
     * @param taskId
     */
    public void takeInProcess(Long taskId) {
        var task = this.getById(taskId);
        StateMachine<TaskState, TaskEvent> taskSM = this.build(task);

        if (!taskHasDeveloper(task)) {
            throw new RuntimeException("There is no developer in task with id=" + taskId);
        }

        boolean isChanged = taskSM.sendEvent(TaskEvent.TAKE_IN_PROCESS);
        if (!isChanged) {
            throw new RuntimeException("Failed to take in process task with id=" + taskId);
        }

        task.setStartDate(new Date());
        task.setState(taskSM.getState().getId());

        taskRepository.save(task);
    }

    private boolean taskHasDeveloper(Task task) {
        return task.getContributors().stream()
                .map(Contributor::getRole)
                .anyMatch(role -> role.equals(Role.DEVELOPER));
    }

    /**
     * Отправить задачу на проверку.
     *
     * @param taskId
     */
    @Override
    public void sendToCheck(Long taskId) {
        var task = this.getById(taskId);
        StateMachine<TaskState, TaskEvent> taskSM = this.build(task);

        boolean isChanged = taskSM.sendEvent(TaskEvent.TO_CHECK);
        if (!isChanged) {
            throw new RuntimeException("Failed to send to check task with id=" + taskId);
        }

        task.setState(taskSM.getState().getId());

        taskRepository.save(task);
    }

    /**
     * Принять задачу.
     *
     * @param taskId
     */
    @Override
    public void approve(Long taskId) {
        var task = this.getById(taskId);
        StateMachine<TaskState, TaskEvent> taskSM = this.build(task);

        boolean isChanged = taskSM.sendEvent(TaskEvent.APPROVE);
        if (!isChanged) {
            throw new RuntimeException("Failed to approve task with id=" + taskId);
        }

        task.setFinishDate(new Date());
        task.setState(taskSM.getState().getId());

        taskRepository.save(task);
    }

    /**
     * Не принять задачу.
     *
     * @param taskId
     */
    @Override
    public void disapprove(Long taskId) {
        var task = this.getById(taskId);
        StateMachine<TaskState, TaskEvent> taskSM = this.build(task);

        boolean isChanged = taskSM.sendEvent(TaskEvent.DISAPPROVE);
        if (!isChanged) {
            throw new RuntimeException("Failed to disapprove task with id=" + taskId);
        }

        task.setState(taskSM.getState().getId());

        taskRepository.save(task);
    }

    /**
     * Изменение задачи.
     *
     * @param taskDto новое состояние задачи.
     */
    @Override
    public void update(Long taskId, TaskDto taskDto) {
        if (!taskRepository.existsById(taskId)) {
            throw new RuntimeException("Task with id=" + taskId +
                    " not exists");
        }

        var task = taskRepository.getOne(taskId);
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        taskRepository.save(task);
    }

    /**
     * Прикрепление контрибутора к задаче.
     *
     * @param contributorId id контрибутора.
     * @param taskId        id задачи, к которой контрибутор прикрепляется.
     */
    @Override
    public void attachContributor(Long contributorId, Long taskId) {
        var task = this.getById(taskId);

        this.addContributor(contributorId, task);

        taskRepository.save(task);
    }

    /**
     * Открепление контрибутора от задачи.
     *
     * @param contributorId id контрибутора.
     * @param taskId        id задачи, от которой контрибутор открепляется.
     */
    @Override
    public void detachContributor(Long contributorId, Long taskId) {
        var task = this.getById(taskId);

        this.removeContributor(contributorId, task);

        taskRepository.save(task);
    }

    private Task getById(Long taskId) {
        var task = taskRepository.findById(taskId);
        return task.orElseThrow(
                () -> new RuntimeException("Task with id=" + taskId + " not exists")
        );
    }

    private StateMachine<TaskState, TaskEvent> build(Task task) {
        String taskSmId = task.getId().toString();
        StateMachine<TaskState, TaskEvent> sm = this.factory.getStateMachine(taskSmId);
        sm.stop();
        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.resetStateMachine(
                            new DefaultStateMachineContext<>(
                                    task.getState(),
                                    null,
                                    null,
                                    null)
                    );
                });
        sm.start();
        return sm;
    }

    private void addContributor(Long contributorId, Task task) {
        var contributors = task.getContributors();

        var contributor = contributorRepository.findById(contributorId);

        if (contributor.isEmpty()) {
            throw new RuntimeException("Contributor with id=" + contributorId +
                    " not exists");
        }

        if (this.isContributorAlreadyAdded(contributor.get(), contributors)) {
            throw new RuntimeException("Contributor with id=" + contributorId +
                    " is already attached to task with id=" + task.getId());
        }

        contributors = Objects.isNull(contributors) ? new LinkedList<>() : contributors;

        contributors.add(contributor.get());

        task.setContributors(contributors);
    }

    private boolean isContributorAlreadyAdded(Contributor contributor,
                                              List<Contributor> contributors) {
        if (Objects.isNull(contributors)) {
            return false;
        } else {
            return contributors.stream()
                    .anyMatch(contrib -> contrib.getId().equals(contributor.getId()));
        }
    }

    private void removeContributor(Long contributorId, Task task) {
        var contributors = task.getContributors();

        boolean isNotAttached = contributors.stream()
                .noneMatch(contributor -> contributor.getId().equals(contributorId));

        if (isNotAttached) {
            throw new RuntimeException("Contributor with id=" + contributorId +
                    " is not attached to task with id=" + task.getId());
        }

        contributors = contributors.stream()
                .filter(contributor -> !contributor.getId().equals(contributorId))
                .collect(Collectors.toList());

        task.setContributors(contributors);
    }

    private Task createFrom(TaskDto taskDto) {
        return Task.builder()
                .id(taskDto.getId())
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .state(taskDto.getState())
                .startDate(taskDto.getStartDate())
                .finishDate(taskDto.getFinishDate())
                .build();
    }
}
