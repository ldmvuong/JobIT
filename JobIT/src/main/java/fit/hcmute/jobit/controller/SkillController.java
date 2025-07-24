package fit.hcmute.jobit.controller;

import com.turkraft.springfilter.boot.Filter;
import fit.hcmute.jobit.dto.request.skill.SkillRequest;
import fit.hcmute.jobit.dto.response.ResultPaginationResponse;
import fit.hcmute.jobit.dto.response.skill.SkillResponse;
import fit.hcmute.jobit.entity.Skill;
import fit.hcmute.jobit.service.SkillService;
import fit.hcmute.jobit.util.annotation.ApiMessage;
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
