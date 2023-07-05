package nl.digitalinvestigation.filecrawl.controller;

import nl.digitalinvestigation.filecrawl.model.Project;
import nl.digitalinvestigation.filecrawl.repository.ProjectRepository;
import nl.digitalinvestigation.filecrawl.repository.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private QueryRepository queryRepository;

    @GetMapping("/projects/create")
    public String getCreate(ModelMap map) {

        map.addAttribute("project", new Project());

        return "/project/create";
    }

    @PostMapping("/projects/create")
    public String add(@ModelAttribute @Valid Project project, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "/project/create";
        }

        Project savedProject = projectRepository.save(project);

        redirectAttributes.addAttribute("success", String.format("Project met ID: %d succesvol aangemaakt.", savedProject.getId()));
        return "redirect:/projects";
    }

    @GetMapping({"/projects", "/"})
    public String viewProjects(ModelMap map) {
        map.addAttribute("projects", projectRepository.findAll());
        return "/project/index";
    }

    @GetMapping("/projects/{id}")
    public String getProjectDetails(@PathVariable Long id, ModelMap map) {
        map.addAttribute("project", projectRepository.findOne(id));
        map.addAttribute("queries", queryRepository.findAllByProjectId(id));
        return "/project/view";
    }

    @GetMapping("/projects/{id}/edit")
    public String getEdit(@PathVariable Long id, ModelMap map) {
        map.addAttribute("project", projectRepository.findOne(id));

        return "/project/edit";
    }

    @PostMapping("/projects/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute @Valid Project project, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "/project/edit";
        }
        Project oldProject = projectRepository.findOne(id);
        oldProject.setName(project.getName());
        oldProject.setTarget(project.getTarget());

        projectRepository.save(oldProject);

        redirectAttributes.addAttribute("success", String.format("Project met ID: %d is succesvol aangepast.", oldProject.getId()));
        return String.format("redirect:/projects/%s", project.getId());
    }

    @GetMapping("/projects/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Project projectToDelete = projectRepository.findOne(id);

        if (projectToDelete == null) {
            redirectAttributes.addAttribute("fail", String.format("Het project met ID: %d kon niet gevonden worden.", id));
            return "redirect:/projects";
        }

        projectRepository.delete(projectToDelete);
        redirectAttributes.addAttribute("success", "Project succesvol gedeletet.");
        return "redirect:/projects";
    }
}
