package nl.digitalinvestigation.filecrawl.config;

import nl.digitalinvestigation.filecrawl.model.Role;
import nl.digitalinvestigation.filecrawl.model.User;
import nl.digitalinvestigation.filecrawl.repository.RoleRepository;
import nl.digitalinvestigation.filecrawl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitialDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    private boolean isSetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {


        if (isSetup)
            return;

        Role adminRole = createRoleIfNotFound("ADMIN");
        Role userRole = createRoleIfNotFound("USER");

        if (userRepository.findByEmail("daan.hoogland@digital-investigation.nl") == null) {
            User user = new User();
            user.setFirstName("Daan");
            user.setLastName("Hoogland");
            user.setPassword(passwordEncoder.encode("a4wh9#7ujLMI!02V"));
            user.setEmail("daan.hoogland@digital-investigation.nl");
            user.setRole(roleRepository.findByName("ADMIN"));
            user.setEnabled(true);
            userRepository.save(user);

//            User normalUser = new User();
//            normalUser.setFirstName("Daan");
//            normalUser.setLastName("Hoogland");
//            normalUser.setPassword(passwordEncoder.encode("123"));
//            normalUser.setEmail("daan.hoogland@digital-investigation.nl");
//            normalUser.setRole(roleRepository.findByName("USER"));
//            normalUser.setEnabled(true);
//            userRepository.save(normalUser);
        }

        isSetup = true;
    }


    @Transactional
    Role createRoleIfNotFound(String roleName) {
        Role role = roleRepository.findByName(roleName);

        if (role == null) {
            role = new Role();
            role.setName(roleName);

            return roleRepository.save(role);
        }

        return role;
    }
}
