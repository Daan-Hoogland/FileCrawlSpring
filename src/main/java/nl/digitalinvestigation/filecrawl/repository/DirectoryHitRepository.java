package nl.digitalinvestigation.filecrawl.repository;

import nl.digitalinvestigation.filecrawl.model.DirectoryHit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface DirectoryHitRepository extends JpaRepository<DirectoryHit, Long> {
    Set<DirectoryHit> findAllByQueryId(Long queryId);
}