package fit.hcmute.JobIT.service.impl;

import fit.hcmute.JobIT.converter.ResumeMapper;
import fit.hcmute.JobIT.dto.request.resume.CreateResumeRequest;
import fit.hcmute.JobIT.dto.request.resume.UpdateResumeRequest;
import fit.hcmute.JobIT.dto.response.ResultPaginationResponse;
import fit.hcmute.JobIT.dto.response.job.JobResponse;
import fit.hcmute.JobIT.dto.response.resume.CreateResumeResponse;
import fit.hcmute.JobIT.dto.response.resume.ResumeReponse;
import fit.hcmute.JobIT.dto.response.resume.UpdateResumeRespone;
import fit.hcmute.JobIT.entity.Job;
import fit.hcmute.JobIT.entity.Resume;
import fit.hcmute.JobIT.entity.User;
import fit.hcmute.JobIT.exception.IdInvalidException;
import fit.hcmute.JobIT.repository.JobRepository;
import fit.hcmute.JobIT.repository.ResumeRepository;
import fit.hcmute.JobIT.repository.UserRepository;
import fit.hcmute.JobIT.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ResumeMapper resumeMapper;

    @Override
    public CreateResumeResponse createResume(CreateResumeRequest request) {

        Optional<User> userOptional = userRepository.findById(request.getUser().getId());

        Optional<Job>  jobOptional = jobRepository.findById(request.getJob().getId());

        if (userOptional.isEmpty() || jobOptional.isEmpty()) {
            throw new IdInvalidException("User or Job not found");
        }

        Resume resume = resumeMapper.toResume(request);
        resume.setUser(userOptional.get());
        resume.setJob(jobOptional.get());

        return resumeMapper.toCreateResponse(
                resumeRepository.save(resume)
        );
    }

    @Override
    public UpdateResumeRespone updateResume(UpdateResumeRequest request) {
        Optional<Resume> resumeOptional = resumeRepository.findById(request.getId());

        if (resumeOptional.isEmpty()) {
            throw new IdInvalidException("Resume not found with id: " + request.getId());
        }

        Resume resume = resumeOptional.get();
        resume.setStatus(request.getStatus());

        return resumeMapper.toUpdateResponse(
                resumeRepository.save(resume)
        );
    }

    @Override
    public void deleteResume(Long resumeId) {
        Optional<Resume> resumeOptional = resumeRepository.findById(resumeId);

        if (resumeOptional.isEmpty()) {
            throw new IdInvalidException("Resume not found with id: " + resumeId);
        }

        resumeRepository.delete(resumeOptional.get());
    }

    @Override
    public ResumeReponse getResumeById(Long resumeId) {
        Optional<Resume> resumeOptional = resumeRepository.findById(resumeId);

        if (resumeOptional.isEmpty()) {
            throw new IdInvalidException("Resume not found with id: " + resumeId);
        }

        return resumeMapper.toResumeReponse(resumeOptional.get());
    }

    @Override
    public ResultPaginationResponse getAllResumes(Specification<Resume> specification, Pageable pageable) {
        Page<Resume> pageResume = resumeRepository.findAll(specification, pageable);

        List<ResumeReponse> resumeResponses = pageResume
                .stream()
                .map(resumeMapper::toResumeReponse)
                .toList();

        ResultPaginationResponse.Meta meta = new ResultPaginationResponse.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pageResume.getTotalPages());
        meta.setTotal(pageResume.getTotalElements());

        ResultPaginationResponse result = new ResultPaginationResponse();
        result.setMeta(meta);
        result.setResult(resumeResponses);

        return result;
    }
}
