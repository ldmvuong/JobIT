package fit.hcmute.JobIT.converter;

import fit.hcmute.JobIT.dto.request.skill.SkillRequest;
import fit.hcmute.JobIT.dto.response.skill.SkillResponse;
import fit.hcmute.JobIT.entity.Skill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    SkillResponse toSkillResponse(Skill skill);
    Skill toSkill(SkillRequest skillRequest);
}
