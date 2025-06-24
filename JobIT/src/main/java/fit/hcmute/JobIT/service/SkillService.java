package fit.hcmute.JobIT.service;

import fit.hcmute.JobIT.dto.request.SkillRequest;
import fit.hcmute.JobIT.dto.response.ResultPaginationResponse;
import fit.hcmute.JobIT.dto.response.skill.SkillResponse;
import fit.hcmute.JobIT.entity.Skill;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface SkillService {
    SkillResponse createSkill(SkillRequest request);
    SkillResponse updateSkill(SkillRequest request);
    ResultPaginationResponse getAllSkills(Specification<Skill> specification, Pageable pageable);
    void deleteSkill(Long id);
}
