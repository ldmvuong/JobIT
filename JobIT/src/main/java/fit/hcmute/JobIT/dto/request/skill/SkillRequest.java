package fit.hcmute.JobIT.dto.request.skill;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SkillRequest {
    Long id;

    @NotBlank(message = "Skill name is required")
    String name;
}
