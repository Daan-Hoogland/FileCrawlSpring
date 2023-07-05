package nl.digitalinvestigation.filecrawl.repository;

import nl.digitalinvestigation.filecrawl.model.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface QueryRepository extends JpaRepository<Query, Long> {

    Set<Query> findAllByProjectId(Long projectId);

}
