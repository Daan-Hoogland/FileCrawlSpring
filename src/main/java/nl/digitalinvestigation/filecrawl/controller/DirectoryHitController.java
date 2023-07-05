package nl.digitalinvestigation.filecrawl.controller;

import nl.digitalinvestigation.filecrawl.model.DirectoryHit;
import nl.digitalinvestigation.filecrawl.model.DirectoryHitJson;
import nl.digitalinvestigation.filecrawl.repository.DirectoryHitRepository;
import nl.digitalinvestigation.filecrawl.repository.ProjectRepository;
import nl.digitalinvestigation.filecrawl.repository.QueryRepository;
import nl.digitalinvestigation.filecrawl.util.DateConverer;
import org.apache.tomcat.jni.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;

@Controller
public class DirectoryHitController {

    @Autowired
    private DirectoryHitRepository directoryHitRepository;

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping("/hits/directory/create")
    public ResponseEntity<Directory> add(@RequestParam(required = false) String platform, @RequestParam(required = false) Long query, @RequestBody DirectoryHitJson directoryHitJson) {
        DirectoryHit directoryHit = new DirectoryHit(directoryHitJson);

        if (platform != null && platform.equals("unix")) {
            directoryHit = DateConverer.generateUnixDate(directoryHit, directoryHitJson);
        } else if (platform != null && platform.equals("win")) {
            try {
                directoryHit = DateConverer.generateWindowsDate(directoryHit, directoryHitJson);
            } catch (ParseException e) {
                System.err.println("Error while converting Windows date.");
                e.printStackTrace();
            }
        } else {
            directoryHit = DateConverer.generateGenericDate(directoryHit, directoryHitJson);
        }

        if (directoryHitJson.getQuery() != null && directoryHitJson.getQuery().isPresent()) {
            directoryHit.setQuery(queryRepository.findOne(directoryHitJson.getQuery().get()));
        } else if (query != null) {
            directoryHit.setQuery(queryRepository.findOne(query));
        }

        DirectoryHit savedDirectoryHit = directoryHitRepository.save(directoryHit);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/projects/{projectId}/queries/{queryId}/hits/directory/{id}")
    public String viewFileHit(@PathVariable Long projectId, @PathVariable Long queryId, @PathVariable Long id, ModelMap map) {
        map.addAttribute("directoryHit", directoryHitRepository.findOne(id));
        map.addAttribute("project", projectRepository.findOne(projectId));
        map.addAttribute("query", queryRepository.findOne(queryId));

        return "/directoryhit/view";
    }

    @GetMapping("/projects/{projectId}/queries/{queryId}/hits/directory/{id}/delete")
    public String deleteFileHit(@PathVariable Long projectId, @PathVariable Long queryId, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        DirectoryHit directoryHitToDelete = directoryHitRepository.findOne(id);

        if (directoryHitToDelete == null) {
            redirectAttributes.addAttribute("fail", String.format("De hit met ID: %d kon niet gevonden worden.", id));
            return String.format("redirect:/projects/%d/queries/%d", projectId, queryId);
        }

        directoryHitRepository.delete(directoryHitToDelete);
        redirectAttributes.addAttribute("success", "Hit succesvol gedeletet");
        return String.format("redirect:/projects/%d/queries/%d", projectId, queryId);
    }
}
