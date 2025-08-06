package fit.hcmute.jobit.service;

import fit.hcmute.jobit.dto.request.job.JobRequest;
import fit.hcmute.jobit.dto.response.ResultPaginationResponse;
import fit.hcmute.jobit.dto.response.job.CreateJobResponse;
import fit.hcmute.jobit.dto.response.job.JobResponse;
import fit.hcmute.jobit.dto.response.job.UpdateJobResponse;
import fit.hcmute.jobit.entity.Job;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public interface JobService {
    JobResponse getJobById(Long id);
    CreateJobResponse createJob(JobRequest jobRequest);
    UpdateJobResponse updateJob(JobRequest jobRequest);
    void deleteJob(Long id);
    ResultPaginationResponse getAllJob(Specification<Job> specification, Pageable pageable);
    List<JobResponse> findJobsByCompany(Long companyId);
}
