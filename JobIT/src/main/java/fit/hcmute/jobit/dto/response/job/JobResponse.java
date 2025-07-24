package fit.hcmute.jobit.dto.response.job;

import fit.hcmute.jobit.dto.response.company.CompanyResponse;
import fit.hcmute.jobit.dto.response.skill.SkillResponse;
import fit.hcmute.jobit.enums.ELevel;
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
    private CompanyResponse company;
    private List<SkillResponse> skills;
}
