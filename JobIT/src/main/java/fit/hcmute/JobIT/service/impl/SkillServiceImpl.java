package fit.hcmute.JobIT.service.impl;

import fit.hcmute.JobIT.converter.SkillMapper;
import fit.hcmute.JobIT.dto.request.SkillRequest;
import fit.hcmute.JobIT.dto.response.ResultPaginationResponse;
import fit.hcmute.JobIT.dto.response.skill.SkillResponse;
import fit.hcmute.JobIT.entity.Skill;
import fit.hcmute.JobIT.exception.IdInvalidException;
import fit.hcmute.JobIT.repository.SkillRepository;
import fit.hcmute.JobIT.service.SkillService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;


    @Override
    public SkillResponse createSkill(SkillRequest request) {
        boolean isExist = skillRepository.existsByName(request.getName());
        if (isExist) {
            throw new IdInvalidException("Skill with name " + request.getName() + " already exists.");
        }

        Skill skill = skillMapper.toSkill(request);
        return skillMapper.toSkillResponse(skillRepository.save(skill));
    }

    @Override
    public SkillResponse updateSkill(SkillRequest request) {
        if (request.getId() == null) {
            throw new IdInvalidException("Skill ID must not be null for update operation.");
        }

        Optional<Skill> optionalSkill = skillRepository.findById(request.getId());
        if (optionalSkill.isEmpty()) {
            throw new IdInvalidException("Skill with id " + request.getId() + " does not exist.");
        }

        Skill skillCurrent = optionalSkill.get();
        if (!skillCurrent.getName().equals(request.getName())) {
            boolean isExist = skillRepository.existsByName(request.getName());
            if (isExist) {
                throw new IdInvalidException("Skill with name " + request.getName() + " already exists.");
            }
            skillCurrent.setName(request.getName());
        }
        return skillMapper.toSkillResponse(skillRepository.save(skillCurrent));
    }

    @Override
    public ResultPaginationResponse getAllSkills(Specification<Skill> specification, Pageable pageable) {
        Page<Skill> skillsPage = skillRepository.findAll(specification, pageable);

        List<SkillResponse> skills = skillsPage
                .getContent()
                .stream()
                .map(skillMapper::toSkillResponse)
                .toList();

        ResultPaginationResponse response = new ResultPaginationResponse();
        ResultPaginationResponse.Meta meta = new ResultPaginationResponse.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(skillsPage.getTotalPages());
        meta.setTotal(skillsPage.getTotalElements());

        response.setMeta(meta);
        response.setResult(skills);
        return response;
    }


    @Override
    @Transactional
    public void deleteSkill(Long id) {

        Optional<Skill> optionalSkill = skillRepository.findById(id);
        if (optionalSkill.isEmpty()) {
            throw new IdInvalidException("Skill with id " + id + " does not exist.");
        }

        // Check if the skill is being used in any job postings
        Skill skill = optionalSkill.get();
        skill.getJobs().forEach(job -> job.getSkills().remove(skill));

        skillRepository.deleteById(id);
    }

}
