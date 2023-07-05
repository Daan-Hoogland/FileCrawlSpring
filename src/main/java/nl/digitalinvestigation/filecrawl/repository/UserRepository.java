package nl.digitalinvestigation.filecrawl.repository;

import nl.digitalinvestigation.filecrawl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
