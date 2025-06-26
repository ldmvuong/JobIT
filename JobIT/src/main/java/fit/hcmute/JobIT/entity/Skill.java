package fit.hcmute.JobIT.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "skills")
@Getter
@Setter
public class Skill extends AbstractEntity{
    private String name;

    @ManyToMany(mappedBy = "skills", fetch = FetchType.LAZY)
    private List<Job> jobs;
}
