package fit.hcmute.jobit.service;

import fit.hcmute.jobit.dto.request.skill.SkillRequest;
import fit.hcmute.jobit.dto.response.ResultPaginationResponse;
import fit.hcmute.jobit.dto.response.skill.SkillResponse;
import fit.hcmute.jobit.entity.Skill;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface SkillService {
    SkillResponse createSkill(SkillRequest request);
    SkillResponse updateSkill(SkillRequest request);
    ResultPaginationResponse getAllSkills(Specification<Skill> specification, Pageable pageable);
    void deleteSkill(Long id);
}
