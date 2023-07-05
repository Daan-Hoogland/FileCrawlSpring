package nl.digitalinvestigation.filecrawl.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "file_hit")
public class FileHit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "base_name")
    private String baseName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "directory_name")
    private String directoryName;

    @Column(name = "hostname")
    private String hostname;

    @Column(name = "ip")
    private String ip;

    @Column(name = "creation_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @Column(name = "last_access_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccessTime;

    @Column(name = "last_write_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastWriteTime;

    @Column(name = "access")
    private String access;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_created")
    private Date dateCreated;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "query_id")
    private Query query;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "directory_hit_id")
    private DirectoryHit directoryHit;

    public FileHit() {
    }

    public FileHit(String name, String baseName, String fullName, String directoryName, String hostname, String ip, Date creationTime, Date lastAccessTime, Date lastWriteTime, String access, Date dateCreated, Query query, DirectoryHit directoryHit) {
        this.name = name;
        this.baseName = baseName;
        this.fullName = fullName;
        this.directoryName = directoryName;
        this.hostname = hostname;
        this.ip = ip;
        this.creationTime = creationTime;
        this.lastAccessTime = lastAccessTime;
        this.lastWriteTime = lastWriteTime;
        this.access = access;
        this.dateCreated = dateCreated;
        this.query = query;
        this.directoryHit = directoryHit;
    }

    public FileHit(FileHitJson json) {
        this.name = json.getName();
        this.baseName = json.getBaseName();
        this.fullName = json.getFullName();
        this.directoryName = json.getDirectoryName();
        this.hostname = json.getHostname();
        this.ip = json.getIp();
        this.access = json.getAccess();
        this.directoryHit = new DirectoryHit(json.getDirectoryHitJson());
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

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Date getLastWriteTime() {
        return lastWriteTime;
    }

    public void setLastWriteTime(Date lastWriteTime) {
        this.lastWriteTime = lastWriteTime;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public DirectoryHit getDirectoryHit() {
        return directoryHit;
    }

    public void setDirectoryHit(DirectoryHit directoryHit) {
        this.directoryHit = directoryHit;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "FileHit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", baseName='" + baseName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", directoryName='" + directoryName + '\'' +
                ", hostname='" + hostname + '\'' +
                ", ip='" + ip + '\'' +
                ", creationTime=" + creationTime +
                ", lastAccessTime=" + lastAccessTime +
                ", lastWriteTime=" + lastWriteTime +
                ", access='" + access + '\'' +
                ", dateCreated=" + dateCreated +
                ", query=" + query +
                ", directoryHit=" + directoryHit +
                '}';
    }
}
