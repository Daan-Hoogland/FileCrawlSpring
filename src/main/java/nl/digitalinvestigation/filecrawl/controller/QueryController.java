package nl.digitalinvestigation.filecrawl.controller;

import nl.digitalinvestigation.filecrawl.model.DirectoryHit;
import nl.digitalinvestigation.filecrawl.model.FileHit;
import nl.digitalinvestigation.filecrawl.model.Project;
import nl.digitalinvestigation.filecrawl.model.Query;
import nl.digitalinvestigation.filecrawl.model.enums.QueryType;
import nl.digitalinvestigation.filecrawl.repository.*;
import nl.digitalinvestigation.filecrawl.util.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class QueryController {

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private FileHitRepository fileHitRepository;

    @Autowired
    private DirectoryHitRepository directoryHitRepository;

    @Autowired
    private ExecutedScriptRepository executedScriptRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/projects/{id}/queries/create")
    public String getCreate(@PathVariable Long id, ModelMap map) {

        Query query = new Query();
        query.setType(QueryType.FILE);
        map.addAttribute("project", projectRepository.findOne(id));
        map.addAttribute("hashAlgorithms", Arrays.asList("", "MD5", "SHA256", "SHA512"));
        map.addAttribute("query", query);
        map.addAttribute("types", new ArrayList<>(Arrays.asList(QueryType.values())));

        return "/query/create";
    }

    @PostMapping("/projects/{id}/queries/create")
    public String add(@PathVariable Long id, @ModelAttribute Query query, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "/query/create";
        }

        query.setProject(projectRepository.findOne(id));
        query.setId(null);
        Project project = projectRepository.findOne(id);
        Query savedQuery = queryRepository.save(query);

        redirectAttributes.addAttribute("success", String.format("Query met ID: %d is succesvol aangemaakt.", savedQuery.getId()));
        return String.format("redirect:/projects/%d", id);
    }


    @GetMapping("/projects/{id}/queries")
    public String viewQueries(@PathVariable Long id, ModelMap map) {
        map.addAttribute("queries", queryRepository.findAllByProjectId(id));
        return "/query/index";
    }

    @GetMapping("/projects/{projectId}/queries/{queryId}")
    public String viewQuery(@PathVariable Long projectId, @PathVariable Long queryId, ModelMap map) {
        map.addAttribute("project", projectRepository.findOne(projectId));
        map.addAttribute("query", queryRepository.findOne(queryId));
        map.addAttribute("fileHits", fileHitRepository.findAllByQueryId(queryId));
        map.addAttribute("directoryHits", directoryHitRepository.findAllByQueryId(queryId));
        map.addAttribute("executedScripts", executedScriptRepository.findAllByQueryId(queryId));

        return "/query/index";
    }

    @GetMapping("/projects/{project}/queries/{id}/edit")
    public String getEdit(@PathVariable Long id, @PathVariable Long project, ModelMap map) {
        map.addAttribute("project", projectRepository.findOne(id));
        map.addAttribute("query", queryRepository.findOne(id));
        map.addAttribute("hashAlgorithms", Arrays.asList("", "MD5", "SHA256", "SHA512"));
        map.addAttribute("types", new ArrayList<>(Arrays.asList(QueryType.values())));

        return "/query/edit";
    }

    @PostMapping("/projects/{project}/queries/{id}/edit")
    public String edit(@PathVariable Long id, @PathVariable Long project, @ModelAttribute Query query, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "/query/edit";
        }

        Query oldQuery = queryRepository.findOne(query.getId());
        oldQuery.setName(query.getName());
        oldQuery.setPaths(query.getPaths());
        oldQuery.setNames(query.getNames());
        oldQuery.setHash(query.getHash());
        oldQuery.setSize(query.getSize());
        oldQuery.setHashAlgorithm(query.getHashAlgorithm());

        queryRepository.save(oldQuery);

        redirectAttributes.addAttribute("success", String.format("Query met ID: %d is succesvol aangepast.", oldQuery.getId()));
        return String.format("redirect:/projects/%d/queries/%d", id, oldQuery.getId());
    }

    @GetMapping("/projects/{projectId}/queries/{id}/delete")
    public String delete(@PathVariable Long projectId, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        Query queryToDelete = queryRepository.findOne(id);

        if (queryToDelete == null) {
            redirectAttributes.addAttribute("fail", String.format("De query met ID: %d kon niet gevonden worden.", id));
            return String.format("redirect:/projects/%d", projectId);
        }

//        fileHitRepository.delete(queryToDelete.getFileHits());
//        directoryHitRepository.delete(queryToDelete.getDirectoryHits());
        queryRepository.delete(queryToDelete);
        redirectAttributes.addAttribute("success", "Query succesvol gedeletet.");
        return String.format("redirect:/projects/%d", projectId);
    }

    @GetMapping(value = "/projects/{project}/queries/zip", produces = "application/zip")
    @ResponseBody
    public HttpEntity<byte[]> getZipForProject(@PathVariable("project") Long projectId, @RequestParam String platform,
                                               @RequestParam(required = false) String type, HttpServletResponse response) {
        List<Query> queries = new ArrayList<>(queryRepository.findAllByProjectId(projectId));
        Project project = projectRepository.findOne(projectId);

        return generateZip(platform, queries, project.getName(), response, type);
    }

    @GetMapping(value = "/projects/{project}/queries/{queryId}/zip", produces = "application/zip")
    @ResponseBody
    public HttpEntity<byte[]> getZipForQuery(@PathVariable("project") Long projectId, @PathVariable Long queryId,
                                             @RequestParam String platform, @RequestParam(required = false) String type,
                                             HttpServletResponse response) {
        List<Query> queries = Arrays.asList(queryRepository.findOne(queryId));

        return generateZip(platform, queries, queries.get(0).getName(), response, type);
    }

    @GetMapping("/projects/{projectId}/queries/{queryId}/hits/delete")
    public String deleteAllFileHits(@PathVariable Long projectId, @PathVariable Long queryId, RedirectAttributes redirectAttributes) {

        Set<FileHit> allFileHits = fileHitRepository.findAllByQueryId(queryId);
        Set<DirectoryHit> allDirectoryHits = directoryHitRepository.findAllByQueryId(queryId);
        fileHitRepository.delete(allFileHits);
        directoryHitRepository.delete(allDirectoryHits);

        redirectAttributes.addAttribute("success", "Hits succesvol gedeletet");
        return String.format("redirect:/projects/%d/queries/%d", projectId, queryId);
    }

    private HttpEntity<byte[]> generateZip(String platform, List<Query> queries, String name, HttpServletResponse response, String type) {
        try {
            switch (platform.toUpperCase()) {
                case "UNIX":
                    return generateUnixZip(response, queries, name);
                case "WIN":
                    return generateWindowsZip(response, queries, name, platform);
                case "LEGWIN":
                    return generateWindowsZip(response, queries, name, platform);
                case "GENERIC":
                    return generateGenericZip(response, queries, name, type);
                default:
                    return generateGenericZip(response, queries, name, type);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private HttpEntity<byte[]> generateGenericZip(HttpServletResponse response, List<Query> queries, String name, String type) throws IOException {
        byte[] buffer = new byte[4096 * 1024];

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        response.setHeader("Content-Disposition", "attachment; filename=" + name + "_Generic_" + type + ".zip");

        ByteArrayInputStream crawlerInput = new ByteArrayInputStream(QueryBuilder.buildJavaQuery(queries, type).getBytes("UTF-8"));
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ZipOutputStream zipStream = new ZipOutputStream(byteStream);

        final Resource resource = resourceLoader.getResource("classpath:crawlers/JavaCrawler.jar");

        InputStream resourceStream = resource.getInputStream();

        zipStream.putNextEntry(new ZipEntry("JavaCrawler.jar"));

        int bytesRead;
        while ((bytesRead = resourceStream.read(buffer)) != -1) {
            zipStream.write(buffer, 0, bytesRead);
        }

        zipStream.closeEntry();
        switch (type.toUpperCase()) {
            case "PS":
                zipStream.putNextEntry(new ZipEntry("run.ps1"));
                break;
            case "BASH":
                zipStream.putNextEntry(new ZipEntry("run"));
                break;
            case "BATCH":
                zipStream.putNextEntry(new ZipEntry("run.bat"));
                break;
        }

        while ((bytesRead = crawlerInput.read(buffer)) != -1) {
            zipStream.write(buffer, 0, bytesRead);
        }

        crawlerInput.close();
        resourceStream.close();
        zipStream.closeEntry();
        zipStream.close();
        byteStream.close();

        return new HttpEntity<>(byteStream.toByteArray(), headers);
    }

    private HttpEntity<byte[]> generateUnixZip(HttpServletResponse response, List<Query> queries, String name) throws IOException {
        byte[] buffer = new byte[4096 * 1024];

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        response.setHeader("Content-Disposition", "attachment; filename=" + name + "_bash.zip");

        ByteArrayInputStream crawlerInput = new ByteArrayInputStream(QueryBuilder.buildUnixQuery(queries).getBytes("UTF-8"));
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ZipOutputStream zipStream = new ZipOutputStream(byteStream);

        final Resource resource = resourceLoader.getResource("classpath:crawlers/bashcrawler");

        InputStream resourceStream = resource.getInputStream();

        zipStream.putNextEntry(new ZipEntry("bashcrawler"));

        int bytesRead;
        while ((bytesRead = resourceStream.read(buffer)) != -1) {
            zipStream.write(buffer, 0, bytesRead);
        }

        zipStream.closeEntry();
        zipStream.putNextEntry(new ZipEntry("run"));

        while ((bytesRead = crawlerInput.read(buffer)) != -1) {
            zipStream.write(buffer, 0, bytesRead);
        }

        crawlerInput.close();
        resourceStream.close();
        zipStream.closeEntry();
        zipStream.close();
        byteStream.close();

        return new HttpEntity<>(byteStream.toByteArray(), headers);
    }

    private HttpEntity<byte[]> generateWindowsZip(HttpServletResponse response, List<Query> queries, String name, String platform) throws IOException {
        byte[] buffer = new byte[4096 * 1024];

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        response.setHeader("Content-Disposition", "attachment; filename=" + name + "_powershell.zip");

        ByteArrayInputStream crawlerInput = new ByteArrayInputStream(QueryBuilder.buildWindowsQuery(queries).getBytes("UTF-8"));
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ZipOutputStream zipStream = new ZipOutputStream(byteStream);

        Resource resource;
        if (platform.toUpperCase().equals("WIN")) {
            resource = resourceLoader.getResource("classpath:crawlers/PSCrawler.ps1");
        } else {
            resource = resourceLoader.getResource("classpath:crawlers/ps2/PSCrawler.ps1");
        }

        InputStream resourceStream = resource.getInputStream();

        zipStream.putNextEntry(new ZipEntry("PSCrawler.ps1"));

        int bytesRead;
        while ((bytesRead = resourceStream.read(buffer)) != -1) {
            zipStream.write(buffer, 0, bytesRead);
        }

        zipStream.closeEntry();

        final Resource curlResource = resourceLoader.getResource("classpath:crawlers/curl.exe");

        InputStream curlResourceStream = curlResource.getInputStream();

        zipStream.putNextEntry(new ZipEntry("curl.exe"));

        int curlBytesRead;
        while ((curlBytesRead = curlResourceStream.read(buffer)) != -1) {
            zipStream.write(buffer, 0, curlBytesRead);
        }

        zipStream.closeEntry();

        zipStream.putNextEntry(new ZipEntry("Run.ps1"));

        int runBytesRead;
        while ((runBytesRead = crawlerInput.read(buffer)) != -1) {
            zipStream.write(buffer, 0, runBytesRead);
        }

        crawlerInput.close();
        curlResourceStream.close();
        zipStream.closeEntry();
        zipStream.close();
        byteStream.close();

        return new HttpEntity<>(byteStream.toByteArray(), headers);
    }
}
