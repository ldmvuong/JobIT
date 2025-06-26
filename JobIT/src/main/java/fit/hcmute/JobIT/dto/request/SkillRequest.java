package fit.hcmute.JobIT.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SkillRequest {
    Long id;

    @NotBlank(message = "Skill name is required")
    String name;
}
