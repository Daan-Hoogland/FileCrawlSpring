package nl.digitalinvestigation.filecrawl.security;

import nl.digitalinvestigation.filecrawl.model.Role;
import nl.digitalinvestigation.filecrawl.model.User;
import nl.digitalinvestigation.filecrawl.repository.RoleRepository;
import nl.digitalinvestigation.filecrawl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service("userDetailsService")
@Transactional
public class FileCrawlUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new org.springframework.security.core.userdetails.User(
                    " ", " ", true, true, true, true,
                    getAuthorities(roleRepository.findByName("USER")));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.isEnabled(), true, true,
                true, getAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));

        return grantedAuthorities;
    }
}
