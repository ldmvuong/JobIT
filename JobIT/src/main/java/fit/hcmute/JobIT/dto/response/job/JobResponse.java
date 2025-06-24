package fit.hcmute.JobIT.dto.response.job;

import fit.hcmute.JobIT.dto.response.skill.SkillResponse;
import fit.hcmute.JobIT.entity.Company;
import fit.hcmute.JobIT.entity.Skill;
import fit.hcmute.JobIT.enums.ELevel;
import fit.hcmute.JobIT.util.SecurityUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class JobResponse {
    private Long id;
    private String name;
    private String location;
    private double salary;
    private int quantity;
    private ELevel level;
    private String description;
    private Instant startDate;
    private Instant endDate;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
    private Company company;
    private List<SkillResponse> skills;
}
