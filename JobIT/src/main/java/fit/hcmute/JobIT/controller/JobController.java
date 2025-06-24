package fit.hcmute.JobIT.controller;

import fit.hcmute.JobIT.dto.request.JobRequest;
import fit.hcmute.JobIT.dto.response.job.CreateJobResponse;
import fit.hcmute.JobIT.dto.response.job.UpdateJobResponse;
import fit.hcmute.JobIT.service.JobService;
import fit.hcmute.JobIT.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @PostMapping
    @ApiMessage("Create a new job")
    public ResponseEntity<CreateJobResponse> createJob(@Valid @RequestBody JobRequest jobRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(jobRequest));
    }

    @PutMapping
    @ApiMessage("Update an existing job")
    public ResponseEntity<UpdateJobResponse> updateJob(@Valid @RequestBody JobRequest jobRequest) {
        return ResponseEntity.ok(jobService.updateJob(jobRequest));
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete a job by ID")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}
