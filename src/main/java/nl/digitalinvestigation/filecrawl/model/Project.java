package nl.digitalinvestigation.filecrawl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "project")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("name")
    @Column(name = "name")
    @NotEmpty
    private String name;

    @JsonProperty("target")
    @Column(name = "target")
    @NotEmpty
    private String target;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Query> queries;

    public Project() {
    }

    public Project(String name, String target, List<Query> queries) {
        this.name = name;
        this.target = target;
        this.queries = queries;
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<Query> getQueries() {
        return queries;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}
