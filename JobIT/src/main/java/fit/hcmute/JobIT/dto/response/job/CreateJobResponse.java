package fit.hcmute.JobIT.dto.response.job;

import fit.hcmute.JobIT.enums.ELevel;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class CreateJobResponse {
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
    List<String> skills;
    private Instant createdAt;
    private String createdBy;
}
