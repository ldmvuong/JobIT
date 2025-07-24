package fit.hcmute.JobIT.controller;

import com.turkraft.springfilter.boot.Filter;
import com.turkraft.springfilter.builder.FilterBuilder;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import fit.hcmute.JobIT.dto.request.resume.CreateResumeRequest;
import fit.hcmute.JobIT.dto.request.resume.UpdateResumeRequest;
import fit.hcmute.JobIT.dto.response.resume.CreateResumeResponse;
import fit.hcmute.JobIT.dto.response.resume.UpdateResumeRespone;
import fit.hcmute.JobIT.entity.AbstractEntity;
import fit.hcmute.JobIT.entity.Company;
import fit.hcmute.JobIT.entity.Resume;
import fit.hcmute.JobIT.entity.User;
import fit.hcmute.JobIT.service.ResumeService;
import fit.hcmute.JobIT.service.UserService;
import fit.hcmute.JobIT.util.SecurityUtil;
import fit.hcmute.JobIT.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final UserService userService;
    private final FilterSpecificationConverter filterSpecificationConverter;
    private final FilterBuilder filterBuilder;

    @PostMapping()
    @ApiMessage("Create a new resume")
    public ResponseEntity<CreateResumeResponse> createResume(@Valid @RequestBody CreateResumeRequest resumeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resumeService.createResume(resumeRequest));
    }

    @PutMapping
    @ApiMessage("Update status of a resume")
    public ResponseEntity<UpdateResumeRespone> updateResume(@Valid @RequestBody UpdateResumeRequest resumeRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(resumeService.updateResume(resumeRequest));
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete a resume by id")
    public ResponseEntity<Void> deleteResume(@PathVariable("id") Long id) {
        resumeService.deleteResume(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @ApiMessage("Get a resume by id")
    public ResponseEntity<?> getResumeById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(resumeService.getResumeById(id));
    }

    @GetMapping()
    @ApiMessage("Get all resumes")
    public ResponseEntity<?> getAllResumes(@Filter Specification<Resume> specification,
                                           Pageable pageable) {
        List<Long> arrJobIds = null;
        String email = SecurityUtil.getCurrentUserLogin().orElse("");

        User currentUser = userService.getUserByEmail(email);
        if(currentUser != null) {
            Company userCompany = currentUser.getCompany();
            if (userCompany != null) {
                arrJobIds = userCompany.getJobs().stream()
                        .map(AbstractEntity::getId)
                        .toList();
            }
        }

        Specification<Resume> jobInSpec = filterSpecificationConverter.convert(filterBuilder.field("job")
                .in(filterBuilder.input(arrJobIds)).get());

        Specification<Resume> finalSpecification = specification.and(jobInSpec);

        return ResponseEntity.ok(resumeService.getAllResumes(finalSpecification, pageable));
    }

    @PostMapping("/by-user")
    @ApiMessage("Get all resumes by user")
    public ResponseEntity<?> getAllResumesByUser(Pageable pageable) {
        return ResponseEntity.ok(resumeService.getAllResumesByUser(pageable));
    }
}
