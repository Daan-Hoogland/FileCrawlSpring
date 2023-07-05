package nl.digitalinvestigation.filecrawl.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExecutedScriptJson {

    @JsonProperty("Hostname")
    private String hostname;

    @JsonProperty("IP")
    private String ip;

    @JsonProperty("TimeExecuted")
    private String timeExecuted;

    @JsonProperty("Query")
    private Long query;

    public ExecutedScriptJson() {
    }

    public ExecutedScriptJson(String hostname, String ip, String timeExecuted, Long query) {
        this.hostname = hostname;
        this.ip = ip;
        this.timeExecuted = timeExecuted;
        this.query = query;
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

    public String getTimeExecuted() {
        return timeExecuted;
    }

    public void setTimeExecuted(String timeExecuted) {
        this.timeExecuted = timeExecuted;
    }

    public Long getQuery() {
        return query;
    }

    public void setQuery(Long query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "ExecutedScriptJson{" +
                ", hostname='" + hostname + '\'' +
                ", ip='" + ip + '\'' +
                ", timeExecuted='" + timeExecuted + '\'' +
                ", query=" + query +
                '}';
    }
}
