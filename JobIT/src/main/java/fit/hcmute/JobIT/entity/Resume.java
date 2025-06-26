package fit.hcmute.JobIT.entity;

import fit.hcmute.JobIT.enums.EResumeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "resumes")
@Getter
@Setter
public class Resume extends AbstractEntity {

    private String email;
    private String url;

    @Enumerated(EnumType.STRING)
    private EResumeStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;
}
