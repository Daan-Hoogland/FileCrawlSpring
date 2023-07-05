package nl.digitalinvestigation.filecrawl.controller;

import nl.digitalinvestigation.filecrawl.model.PasswordReset;
import nl.digitalinvestigation.filecrawl.model.User;
import nl.digitalinvestigation.filecrawl.repository.RoleRepository;
import nl.digitalinvestigation.filecrawl.repository.UserRepository;
import nl.digitalinvestigation.filecrawl.security.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @GetMapping("/users/create")
    public String createUser(ModelMap map) {

        map.addAttribute("user", new User(true));
        map.addAttribute("roles", roleRepository.findAll());

        return "users/create";
    }

    @PostMapping("/users/create")
    public String createUserPost(@ModelAttribute @Valid User user, BindingResult result, ModelMap map, RedirectAttributes redirectAttributes) {

        if (user.getEmail() != null && userRepository.findByEmail(user.getEmail()) != null) {
            result.rejectValue("email", "user.email.used");
        }

        if (result.hasErrors()) {
            map.addAttribute("roles", roleRepository.findAll());

            return "users/create";
        }

        System.err.println("Has no errors, saving:");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        User savedUser = userRepository.save(user);

        redirectAttributes.addAttribute("success", String.format("Gebruiker met ID: %d is succesvol aangemaakt.", savedUser.getId()));
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getUsers(ModelMap map) {
        Authentication authentication = authenticationFacade.getAuthentication();
        map.addAttribute("users", userRepository.findAll());

        return "users/index";
    }

    @GetMapping("/users/{id}/edit")
    public String editUser(@PathVariable Long id, ModelMap map) {

        User user = userRepository.findOne(id);
        map.addAttribute("user", user);
        map.addAttribute("roles", roleRepository.findAll());

        return "users/edit";
    }

    @PostMapping("/users/{id}/edit")
    public String editUserPost(@PathVariable Long id, @ModelAttribute @Valid User user, ModelMap map, BindingResult result, RedirectAttributes redirectAttributes) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            result.rejectValue("email", "user.email.used");
            map.addAttribute("roles", roleRepository.findAll());
            return "users/edit";
        }
        if (result.hasErrors()) {
            return "users/edit";
        }

        User oldUser = userRepository.findOne(user.getId());
        oldUser.setEmail(user.getEmail());
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setRole(user.getRole());
        oldUser.setPassword(user.getPassword());

        userRepository.save(oldUser);

        redirectAttributes.addAttribute("success", String.format("Gebruiker met ID: %d is succesvol aangepast.", oldUser.getId()));
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/reset")
    public String resetUserPasswordPost(@PathVariable Long id, ModelMap map) {
        map.addAttribute("passwordReset", new PasswordReset());
        map.addAttribute("user", userRepository.findOne(id));
        return "users/reset";
    }

    @PostMapping("/users/{id}/reset")
    public String resetUserPasswordPost(@PathVariable Long id, @ModelAttribute PasswordReset passwordReset, RedirectAttributes redirectAttributes) {
        User user = userRepository.findOne(id);
        user.setPassword(passwordEncoder.encode(passwordReset.getNewPassword()));
        User savedUser = userRepository.save(user);

        redirectAttributes.addAttribute("success", String.format("Wachtwoord voor gebruiker met ID: %d is succesvol aangepast.", savedUser.getId()));
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/status")
    public String changeUserStatus(@PathVariable Long id, ModelMap map, RedirectAttributes redirectAttributes) {
        User user = userRepository.findOne(id);
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);

        redirectAttributes.addAttribute("success", String.format("Status van gebruiker met ID: %d is veranderd.", user.getId()));
        return "redirect:/users";
    }

    @GetMapping("/account")
    public String accountInformation(ModelMap map) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        map.addAttribute("user", user);
        return "users/dashboard/index";
    }

    @GetMapping("/account/edit")
    public String editAccountInformation(ModelMap map) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        map.addAttribute("user", user);
        return "users/dashboard/edit";
    }

    @PostMapping("/account/edit")
    public String editAccountInformationPost(@ModelAttribute @Valid User user, BindingResult result, ModelMap map, RedirectAttributes redirectAttributes) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            result.rejectValue("email", "user.email.used");
            return "users/dashboard/edit";
        }
        if (result.hasErrors()) {
            return "users/dashboard/edit";
        }

        redirectAttributes.addAttribute("success", "Uw accountgegevens zijn aangepast.");
        return "redirect:/account";
    }

    @GetMapping("/account/reset")
    public String resetPassword(ModelMap map) {
        map.addAttribute("passwordReset", new PasswordReset());
        return "users/dashboard/reset";
    }

    @PostMapping("/account/reset")
    public String resetPassword(@ModelAttribute @Valid PasswordReset passwordReset, BindingResult result, ModelMap map, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        if (!passwordEncoder.matches(passwordReset.getCurrentPassword(), user.getPassword())) {
            result.rejectValue("currentPassword", "user.reset.fail");
            return "users/dashboard/reset";
        }

        user.setPassword(passwordEncoder.encode(passwordReset.getNewPassword()));
        userRepository.save(user);
        redirectAttributes.addAttribute("success", "Uw wachtwoord is succesvol aangepast.");
        return "redirect:/account";
    }

}
