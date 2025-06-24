package fit.hcmute.JobIT.dto.request;


import fit.hcmute.JobIT.enums.ELevel;
import fit.hcmute.JobIT.util.annotation.enumvalidate.subnet.EnumSubset;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;


@Getter
@Setter
public class JobRequest {
    private Long id;
    private String name;
    private String location;
    private double salary;
    private int quantity;

    @EnumSubset(enumClass = ELevel.class)
    private ELevel level;

    private String description;
    private Instant startDate;
    private Instant endDate;
    private boolean active;
    List<SkillId> skills;

    @Getter
    @Setter
    public static class SkillId {
        Long id;
    }
}
