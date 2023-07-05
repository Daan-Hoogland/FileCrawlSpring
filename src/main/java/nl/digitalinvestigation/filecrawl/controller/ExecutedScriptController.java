package nl.digitalinvestigation.filecrawl.controller;

import nl.digitalinvestigation.filecrawl.model.ExecutedScript;
import nl.digitalinvestigation.filecrawl.model.ExecutedScriptJson;
import nl.digitalinvestigation.filecrawl.repository.ExecutedScriptRepository;
import nl.digitalinvestigation.filecrawl.repository.QueryRepository;
import nl.digitalinvestigation.filecrawl.util.DateConverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.Date;

@Controller
public class ExecutedScriptController {

    @Autowired
    private ExecutedScriptRepository executedScriptRepository;

    @Autowired
    private QueryRepository queryRepository;

    @PostMapping("/hits/execute/create")
    public ResponseEntity<ExecutedScript> createNoResult(@RequestBody ExecutedScriptJson executedScriptJson, @RequestParam Long query, @RequestParam String platform) {

        ExecutedScript executedScript = executedScriptRepository.findByIpAndQueryId(executedScriptJson.getIp(), executedScriptJson.getQuery());


        if (executedScript != null) {
            switch (platform.toUpperCase()) {
                case "WIN":
                    try {
                        executedScript.setTimeExecuted(DateConverer.convertWindowsToDate(executedScriptJson.getTimeExecuted()));
                    } catch (ParseException e) {
                        executedScript.setTimeExecuted(new Date());
                    }
                    break;
                case "UNIX":
                    executedScript.setTimeExecuted(new Date(Long.parseLong(executedScriptJson.getTimeExecuted()) * 1000));
                    break;
                case "JAVA":

                    break;
            }
        } else {
            executedScript = new ExecutedScript();
            executedScript.setHostname(executedScriptJson.getHostname());
            executedScript.setIp(executedScriptJson.getIp());
            executedScript.setQuery(queryRepository.findOne(query));
            switch (platform.toUpperCase()) {
                case "WIN":
                    try {
                        executedScript.setTimeExecuted(DateConverer.convertWindowsToDate(executedScriptJson.getTimeExecuted()));
                    } catch (ParseException e) {
                        executedScript.setTimeExecuted(new Date());
                    }
                    break;
                case "UNIX":
                    executedScript.setTimeExecuted(new Date(Long.parseLong(executedScriptJson.getTimeExecuted())));
                    break;
                case "JAVA":

                    break;
            }
        }

        executedScriptRepository.save(executedScript);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
