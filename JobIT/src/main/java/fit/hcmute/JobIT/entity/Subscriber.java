package fit.hcmute.JobIT.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "subscribers")
@Getter
@Setter
public class Subscriber extends AbstractEntity{

    private String name;
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name= "subscriber_skill",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    List<Skill> skills;
}
