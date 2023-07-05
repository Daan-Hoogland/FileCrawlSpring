package nl.digitalinvestigation.filecrawl.model;

import nl.digitalinvestigation.filecrawl.model.enums.QueryType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "query")
public class Query implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotEmpty
    private String name;

    @Column(name = "names")
    private String names;

    @Column(name = "paths")
    private String paths;

    @Column(name = "hash")
    private String hash;

    @Column(name = "hash_algorithm")
    private String hashAlgorithm;

    @Column(name = "size")
    private Long size;

    @Column(name = "type")
    @NotNull
    private QueryType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "query", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<FileHit> fileHits;

    @OneToMany(mappedBy = "query", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<DirectoryHit> directoryHits;

    @OneToMany(mappedBy = "query", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<ExecutedScript> executedScriptSet;

    public Query() {
    }

    public Query(String name, String names, String paths, String hash, String hashAlgorithm, Long size, QueryType type, Project project, Set<FileHit> fileHits, Set<DirectoryHit> directoryHits) {
        this.name = name;
        this.names = names;
        this.paths = paths;
        this.hash = hash;
        this.hashAlgorithm = hashAlgorithm;
        this.size = size;
        this.type = type;
        this.project = project;
        this.fileHits = fileHits;
        this.directoryHits = directoryHits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    public void setHashAlgorithm(String hashAlgorithm) {
        this.hashAlgorithm = hashAlgorithm;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public QueryType getType() {
        return type;
    }

    public void setType(QueryType type) {
        this.type = type;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<FileHit> getFileHits() {
        return fileHits;
    }

    public void setFileHits(Set<FileHit> fileHits) {
        this.fileHits = fileHits;
    }

    public Set<DirectoryHit> getDirectoryHits() {
        return directoryHits;
    }

    public void setDirectoryHits(Set<DirectoryHit> directoryHits) {
        this.directoryHits = directoryHits;
    }

    public Set<ExecutedScript> getExecutedScriptSet() {
        return executedScriptSet;
    }

    public void setExecutedScriptSet(Set<ExecutedScript> executedScriptSet) {
        this.executedScriptSet = executedScriptSet;
    }

    @Override
    public String toString() {
        return "Query{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", names='" + names + '\'' +
                ", paths='" + paths + '\'' +
                ", hash='" + hash + '\'' +
                ", hashAlgorithm='" + hashAlgorithm + '\'' +
                ", size=" + size +
                ", project=" + project +
                '}';
    }
}
