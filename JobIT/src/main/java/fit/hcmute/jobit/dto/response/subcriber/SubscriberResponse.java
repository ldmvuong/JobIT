package fit.hcmute.jobit.dto.response.subcriber;

import fit.hcmute.jobit.dto.response.skill.SkillResponse;
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
