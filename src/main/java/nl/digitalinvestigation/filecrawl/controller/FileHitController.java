package nl.digitalinvestigation.filecrawl.controller;

import nl.digitalinvestigation.filecrawl.model.DirectoryHit;
import nl.digitalinvestigation.filecrawl.model.FileHit;
import nl.digitalinvestigation.filecrawl.model.FileHitJson;
import nl.digitalinvestigation.filecrawl.repository.DirectoryHitRepository;
import nl.digitalinvestigation.filecrawl.repository.FileHitRepository;
import nl.digitalinvestigation.filecrawl.repository.ProjectRepository;
import nl.digitalinvestigation.filecrawl.repository.QueryRepository;
import nl.digitalinvestigation.filecrawl.util.DateConverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;

@Controller
public class FileHitController {

    @Autowired
    private FileHitRepository fileHitRepository;

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DirectoryHitRepository directoryHitRepository;

    @PostMapping("/hits/file/create")
    public ResponseEntity<FileHit> add(@RequestParam(required = false) String platform, @RequestParam(required = false) Long query, @RequestBody FileHitJson hit) {

        FileHit fileHit = new FileHit(hit);

        if (platform != null && platform.equals("unix")) {
            fileHit = DateConverer.generateUnixDate(fileHit, hit);
        } else if (platform != null && platform.equals("win")) {
            try {
                fileHit = DateConverer.generateWindowsDate(fileHit, hit);
            } catch (ParseException e) {
                System.err.println("Error while converting Windows date.");
                e.printStackTrace();
            }
        } else {
            fileHit = DateConverer.generateGenericDate(fileHit, hit);
        }

        fileHit.setQuery(queryRepository.findOne(hit.getQuery()));

        DirectoryHit savedDirectory = directoryHitRepository.save(fileHit.getDirectoryHit());
        fileHit.setDirectoryHit(savedDirectory);
        FileHit savedFileHit = fileHitRepository.save(fileHit);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/projects/{projectId}/queries/{queryId}/hits/file/{id}")
    public String viewFileHit(@PathVariable Long projectId, @PathVariable Long queryId, @PathVariable Long id, ModelMap map) {

        map.addAttribute("fileHit", fileHitRepository.findOne(id));
        map.addAttribute("project", projectRepository.findOne(projectId));
        map.addAttribute("query", queryRepository.findOne(queryId));

        return "/filehit/view";
    }

    @GetMapping("/projects/{projectId}/queries/{queryId}/hits/file/{id}/delete")
    public String deleteFileHit(@PathVariable Long projectId, @PathVariable Long queryId, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        FileHit fileHitToDelete = fileHitRepository.findOne(id);

        if (fileHitToDelete == null) {
            redirectAttributes.addAttribute("fail", String.format("De hit met ID: %d kon niet gevonden worden.", id));
            return String.format("redirect:/projects/%d/queries/%d", projectId, queryId);
        }

        fileHitRepository.delete(fileHitToDelete);
        redirectAttributes.addAttribute("success", "Hit succesvol gedeletet");
        return String.format("redirect:/projects/%d/queries/%d", projectId, queryId);
    }
}
