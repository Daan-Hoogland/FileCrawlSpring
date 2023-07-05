package nl.digitalinvestigation.filecrawl.repository;

import nl.digitalinvestigation.filecrawl.model.FileHit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FileHitRepository extends JpaRepository<FileHit, Long> {
    Set<FileHit> findAllByQueryId(Long queryId);
}
