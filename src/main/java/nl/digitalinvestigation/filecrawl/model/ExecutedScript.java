package nl.digitalinvestigation.filecrawl.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "executed_script")
public class ExecutedScript {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hostname")
    @JsonProperty("Hostname")
    private String hostname;

    @Column(name = "ip")
    @JsonProperty("IP")
    private String ip;

    @Column(name = "time_executed")
    @JsonProperty("TimeExecuted")
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeExecuted;

    @JsonProperty("Query")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "query_id")
    private Query query;

    public ExecutedScript() {
    }

    public ExecutedScript(String hostname, String ip, Date timeExecuted, Query query) {
        this.hostname = hostname;
        this.ip = ip;
        this.timeExecuted = timeExecuted;
        this.query = query;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getTimeExecuted() {
        return timeExecuted;
    }

    public void setTimeExecuted(Date timeExecuted) {
        this.timeExecuted = timeExecuted;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "ExecutedScript{" +
                "id=" + id +
                ", hostname='" + hostname + '\'' +
                ", ip='" + ip + '\'' +
                ", timeExecuted=" + timeExecuted +
                ", query=" + query.getId() +
                '}';
    }
}
