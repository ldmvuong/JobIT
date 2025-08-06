package fit.hcmute.jobit.controller;

import com.turkraft.springfilter.boot.Filter;
import fit.hcmute.jobit.dto.request.job.JobRequest;
import fit.hcmute.jobit.dto.response.ResultPaginationResponse;
import fit.hcmute.jobit.dto.response.job.CreateJobResponse;
import fit.hcmute.jobit.dto.response.job.JobResponse;
import fit.hcmute.jobit.dto.response.job.UpdateJobResponse;
import fit.hcmute.jobit.dto.response.resume.ResumeReponse;
import fit.hcmute.jobit.entity.Job;
import fit.hcmute.jobit.service.JobService;
import fit.hcmute.jobit.service.ResumeService;
import fit.hcmute.jobit.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;
    private final ResumeService resumeService;

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
        return ResponseEntity.ok(null);
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

    @GetMapping("/{id}/resumes")
    @ApiMessage("Get all resumes by job ID")
    public ResponseEntity<List<ResumeReponse>> getAllResumesByJobId(@PathVariable Long id) {
        return ResponseEntity.ok(resumeService.getResumesByJobId(id));
    }

}
