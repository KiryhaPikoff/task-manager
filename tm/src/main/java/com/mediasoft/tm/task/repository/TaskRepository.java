package com.mediasoft.tm.task.repository;

import com.mediasoft.tm.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT DISTINCT t FROM Task t JOIN Contributor c ON c.project.id=:projectId")
    List<Task> getDistinctByContributors_ProjectId(Long projectId);
}
