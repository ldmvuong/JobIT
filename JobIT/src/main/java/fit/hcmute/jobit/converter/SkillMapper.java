package fit.hcmute.jobit.converter;

import fit.hcmute.jobit.dto.request.skill.SkillRequest;
import fit.hcmute.jobit.dto.response.skill.SkillResponse;
import fit.hcmute.jobit.entity.Skill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    SkillResponse toSkillResponse(Skill skill);
    Skill toSkill(SkillRequest skillRequest);
}
