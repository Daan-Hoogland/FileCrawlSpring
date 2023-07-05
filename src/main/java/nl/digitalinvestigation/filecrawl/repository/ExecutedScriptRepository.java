package nl.digitalinvestigation.filecrawl.repository;

import nl.digitalinvestigation.filecrawl.model.ExecutedScript;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ExecutedScriptRepository extends JpaRepository<ExecutedScript, Long> {

    Set<ExecutedScript> findAllByQueryId(Long queryId);

    ExecutedScript findByIpAndQueryId(String ip, Long queryId);
}
