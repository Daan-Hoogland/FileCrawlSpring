package nl.digitalinvestigation.filecrawl.repository;

import nl.digitalinvestigation.filecrawl.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
