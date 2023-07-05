package nl.digitalinvestigation.filecrawl.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "directory_hit")
public class DirectoryHit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "access")
    private String access;

    @Column(name = "creation_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @Column(name = "last_access_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccessTime;

    @Column(name = "last_write_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastWriteTime;

    @Column(name = "hostname")
    private String hostname;

    @Column(name = "ip")
    private String ip;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "query_id")
    private Query query;

    public DirectoryHit() {
    }

    public DirectoryHit(String name, String access, Date creationTime, Date lastAccessTime, Date lastWriteTime, String hostname, String ip, Query query) {
        this.name = name;
        this.access = access;
        this.creationTime = creationTime;
        this.lastAccessTime = lastAccessTime;
        this.lastWriteTime = lastWriteTime;
        this.hostname = hostname;
        this.ip = ip;
        this.query = query;
    }

    public DirectoryHit(DirectoryHitJson directoryHitJson) {
        this.name = directoryHitJson.getName();
        this.access = directoryHitJson.getAccess();
        this.hostname = directoryHitJson.getHostname();
        this.ip = directoryHitJson.getIp();
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

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
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

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "DirectoryHit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", access='" + access + '\'' +
                ", creationTime=" + creationTime +
                ", lastAccessTime=" + lastAccessTime +
                ", lastWriteTime=" + lastWriteTime +
                ", hostname='" + hostname + '\'' +
                ", ip='" + ip + '\'' +
                ", query=" + query +
                '}';
    }
}