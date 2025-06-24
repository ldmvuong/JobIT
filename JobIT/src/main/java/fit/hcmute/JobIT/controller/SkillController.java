package fit.hcmute.JobIT.controller;

import com.turkraft.springfilter.boot.Filter;
import fit.hcmute.JobIT.dto.request.SkillRequest;
import fit.hcmute.JobIT.dto.response.ResultPaginationResponse;
import fit.hcmute.JobIT.dto.response.skill.SkillResponse;
import fit.hcmute.JobIT.entity.Skill;
import fit.hcmute.JobIT.service.SkillService;
import fit.hcmute.JobIT.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/skills")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @PostMapping
    @ApiMessage("Add a new skill")
    public ResponseEntity<SkillResponse> addSkill(@Valid @RequestBody SkillRequest skillRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(skillService.createSkill(skillRequest));
    }

    @PutMapping
    @ApiMessage("Update an existing skill")
    public ResponseEntity<SkillResponse> updateSkill(@Valid @RequestBody SkillRequest skillRequest) {
        return ResponseEntity.ok(skillService.updateSkill(skillRequest));
    }

    @GetMapping
    @ApiMessage("Get all skills")
    public ResponseEntity<ResultPaginationResponse> getAllSkills(
            @Filter Specification<Skill> specification,
            Pageable pageable
    ) {
        return ResponseEntity.ok(skillService.getAllSkills(specification, pageable));
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete a skill by ID")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }
}
