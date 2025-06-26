package fit.hcmute.JobIT.entity;

import fit.hcmute.JobIT.enums.ELevel;
import fit.hcmute.JobIT.util.SecurityUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "jobs")
@Getter
@Setter
public class Job extends AbstractEntity {

    private String name;
    private String location;
    private double salary;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private ELevel level;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    private Instant startDate;
    private Instant endDate;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    // Job là chủ nhờ Join Table, tự động quản lý quan hệ nhiều-nhiều với Skill
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "job_skill",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills;

    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY)
    private List<Resume> resumes;

}
