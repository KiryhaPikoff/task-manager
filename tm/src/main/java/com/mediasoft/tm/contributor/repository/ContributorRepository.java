package com.mediasoft.tm.contributor.repository;

import com.mediasoft.tm.contributor.model.Contributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContributorRepository extends JpaRepository<Contributor, Long> {

    boolean existsById(Long contributorId);

    boolean existsByAccountIdAndProjectId(Long accountId, Long projectId);

    Optional<Contributor> findByAccountIdAndProjectId(Long accountId, Long projectId);
}
