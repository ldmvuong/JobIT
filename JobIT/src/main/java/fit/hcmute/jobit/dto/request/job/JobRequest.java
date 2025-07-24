package fit.hcmute.jobit.dto.request.job;


import fit.hcmute.jobit.enums.ELevel;
import fit.hcmute.jobit.util.annotation.enumvalidate.subnet.EnumSubset;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;


@Getter
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
