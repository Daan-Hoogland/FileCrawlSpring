package nl.digitalinvestigation.filecrawl.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Optional;

public class DirectoryHitJson implements Serializable {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Access")
    private String access;

    @JsonProperty("CreationTime")
    private String creationTime;

    @JsonProperty("LastAccessTime")
    private String lastAccessTime;

    @JsonProperty("LastWriteTime")
    private String lastWriteTime;

    @JsonProperty("Hostname")
    private String hostname;

    @JsonProperty("IP")
    private String ip;

    @JsonProperty("QueryID")
    private Optional<Long> query;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
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

    public Optional<Long> getQuery() {
        return query;
    }

    public void setQuery(Long query) {
        this.query = Optional.of(query);
    }

    @Override
    public String toString() {
        return "DirectoryHitJson{" +
                "name='" + name + '\'' +
                ", access='" + access + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", lastAccessTime='" + lastAccessTime + '\'' +
                ", lastWriteTime='" + lastWriteTime + '\'' +
                ", hostname='" + hostname + '\'' +
                ", ip='" + ip + '\'' +
                ", query=" + query +
                '}';
    }
}
