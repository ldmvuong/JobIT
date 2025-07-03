package fit.hcmute.JobIT.dto.response.subcriber;

import fit.hcmute.JobIT.dto.response.skill.SkillResponse;
import fit.hcmute.JobIT.entity.Skill;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubscriberResponse {
    private Long id;
    private String name;
    private String email;
    private List<SkillResponse> skills;
}
