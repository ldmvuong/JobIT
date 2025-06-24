package fit.hcmute.JobIT.service;

import fit.hcmute.JobIT.dto.request.JobRequest;
import fit.hcmute.JobIT.dto.response.ResultPaginationResponse;
import fit.hcmute.JobIT.dto.response.job.CreateJobResponse;
import fit.hcmute.JobIT.dto.response.job.JobResponse;
import fit.hcmute.JobIT.dto.response.job.UpdateJobResponse;
import fit.hcmute.JobIT.entity.Job;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


public interface JobService {
    JobResponse getJobById(Long id);
    CreateJobResponse createJob(JobRequest jobRequest);
    UpdateJobResponse updateJob(JobRequest jobRequest);
    void deleteJob(Long id);
    ResultPaginationResponse getAllJob(Specification<Job> specification, Pageable pageable);
}
