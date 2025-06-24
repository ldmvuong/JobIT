package fit.hcmute.JobIT.controller;

import com.turkraft.springfilter.boot.Filter;
import fit.hcmute.JobIT.dto.request.JobRequest;
import fit.hcmute.JobIT.dto.response.ResultPaginationResponse;
import fit.hcmute.JobIT.dto.response.job.CreateJobResponse;
import fit.hcmute.JobIT.dto.response.job.JobResponse;
import fit.hcmute.JobIT.dto.response.job.UpdateJobResponse;
import fit.hcmute.JobIT.entity.Job;
import fit.hcmute.JobIT.service.JobService;
import fit.hcmute.JobIT.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    @GetMapping("/{id}")
    @ApiMessage("Get a job by ID")
    public ResponseEntity<JobResponse> getJob(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @GetMapping
    @ApiMessage("Get all jobs")
    public ResponseEntity<ResultPaginationResponse> getAllJobs(@Filter Specification<Job> specification, Pageable pageable) {
        return ResponseEntity.ok(jobService.getAllJob(specification, pageable));
    }
}
