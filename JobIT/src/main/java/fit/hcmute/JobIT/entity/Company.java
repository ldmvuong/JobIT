package fit.hcmute.JobIT.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company extends AbstractEntity{
    private String name;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    private String address;
    private String logo;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    List<User> users;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    List<Job> jobs;
}
