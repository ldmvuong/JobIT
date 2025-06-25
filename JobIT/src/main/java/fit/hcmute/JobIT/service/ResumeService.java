package fit.hcmute.JobIT.service;

import fit.hcmute.JobIT.dto.request.resume.CreateResumeRequest;
import fit.hcmute.JobIT.dto.response.resume.CreateResumeResponse;

public interface ResumeService {
    CreateResumeResponse createResume(CreateResumeRequest request);
}
