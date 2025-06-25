package fit.hcmute.JobIT.controller;

import fit.hcmute.JobIT.dto.request.resume.CreateResumeRequest;
import fit.hcmute.JobIT.dto.response.resume.CreateResumeResponse;
import fit.hcmute.JobIT.service.ResumeService;
import fit.hcmute.JobIT.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
