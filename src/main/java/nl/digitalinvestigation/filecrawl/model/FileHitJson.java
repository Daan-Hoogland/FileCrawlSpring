package nl.digitalinvestigation.filecrawl.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileHitJson {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("BaseName")
    private String baseName;

    @JsonProperty("FullName")
    private String fullName;

    @JsonProperty("DirectoryName")
    private String directoryName;

    @JsonProperty("Hostname")
    private String hostname;

    @JsonProperty("IP")
    private String ip;

    @JsonProperty("CreationTime")
    private String creationTime;

    @JsonProperty("LastAccessTime")
    private String lastAccessTime;

    @JsonProperty("LastWriteTime")
    private String lastWriteTime;

    @JsonProperty("Access")
    private String access;

    @JsonProperty("Directory")
    private DirectoryHitJson directoryHitJson;

    @JsonProperty("QueryID")
    private Long query;

    public FileHitJson() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(String lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public String getLastWriteTime() {
        return lastWriteTime;
    }

    public void setLastWriteTime(String lastWriteTime) {
        this.lastWriteTime = lastWriteTime;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public DirectoryHitJson getDirectoryHitJson() {
        return directoryHitJson;
    }

    public void setDirectoryHitJson(DirectoryHitJson directoryHitJson) {
        this.directoryHitJson = directoryHitJson;
    }

    public Long getQuery() {
        return query;
    }

    public void setQuery(Long query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "FileHitJson{" +
                "name='" + name + '\'' +
                ", baseName='" + baseName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", directoryName='" + directoryName + '\'' +
                ", hostname='" + hostname + '\'' +
                ", ip='" + ip + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", lastAccessTime='" + lastAccessTime + '\'' +
                ", lastWriteTime='" + lastWriteTime + '\'' +
                ", access='" + access + '\'' +
                ", directoryHitJson=" + directoryHitJson +
                ", query=" + query +
                '}';
    }
}
