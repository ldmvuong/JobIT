package fit.hcmute.JobIT.controller;

import com.turkraft.springfilter.boot.Filter;
import fit.hcmute.JobIT.dto.request.resume.CreateResumeRequest;
import fit.hcmute.JobIT.dto.request.resume.UpdateResumeRequest;
import fit.hcmute.JobIT.dto.response.resume.CreateResumeResponse;
import fit.hcmute.JobIT.dto.response.resume.UpdateResumeRespone;
import fit.hcmute.JobIT.entity.Resume;
import fit.hcmute.JobIT.service.ResumeService;
import fit.hcmute.JobIT.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

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
        return ResponseEntity.ok(resumeService.getAllResumes(specification, pageable));
    }
}
