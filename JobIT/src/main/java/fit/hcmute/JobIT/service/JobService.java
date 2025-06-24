package fit.hcmute.JobIT.service;

import fit.hcmute.JobIT.dto.request.JobRequest;
import fit.hcmute.JobIT.dto.response.job.CreateJobResponse;
import fit.hcmute.JobIT.dto.response.job.UpdateJobResponse;

public interface JobService {
    CreateJobResponse createJob(JobRequest jobRequest);
    UpdateJobResponse updateJob(JobRequest jobRequest);
    void deleteJob(Long id);
}
