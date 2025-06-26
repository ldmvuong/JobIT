package fit.hcmute.JobIT.service;

import fit.hcmute.JobIT.dto.request.resume.CreateResumeRequest;
import fit.hcmute.JobIT.dto.request.resume.UpdateResumeRequest;
import fit.hcmute.JobIT.dto.response.ResultPaginationResponse;
import fit.hcmute.JobIT.dto.response.resume.CreateResumeResponse;
import fit.hcmute.JobIT.dto.response.resume.ResumeReponse;
import fit.hcmute.JobIT.dto.response.resume.UpdateResumeRespone;
import fit.hcmute.JobIT.entity.Resume;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ResumeService {
    CreateResumeResponse createResume(CreateResumeRequest request);
    UpdateResumeRespone updateResume(UpdateResumeRequest request);
    void deleteResume(Long resumeId);
    ResumeReponse getResumeById(Long resumeId);
    ResultPaginationResponse getAllResumes(Specification<Resume> specification, Pageable pageable);

}
