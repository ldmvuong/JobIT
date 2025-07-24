package fit.hcmute.jobit.dto.response.job;

import fit.hcmute.jobit.enums.ELevel;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class UpdateJobResponse {
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
    private Instant updatedAt;
    private String updatedBy;
}
