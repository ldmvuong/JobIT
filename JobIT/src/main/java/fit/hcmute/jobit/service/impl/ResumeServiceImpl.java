package fit.hcmute.jobit.service.impl;

import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import com.turkraft.springfilter.parser.FilterParser;
import com.turkraft.springfilter.parser.node.FilterNode;
import fit.hcmute.jobit.converter.ResumeMapper;
import fit.hcmute.jobit.dto.request.resume.CreateResumeRequest;
import fit.hcmute.jobit.dto.request.resume.UpdateResumeRequest;
import fit.hcmute.jobit.dto.response.ResultPaginationResponse;
import fit.hcmute.jobit.dto.response.resume.CreateResumeResponse;
import fit.hcmute.jobit.dto.response.resume.ResumeReponse;
import fit.hcmute.jobit.dto.response.resume.UpdateResumeRespone;
import fit.hcmute.jobit.entity.Job;
import fit.hcmute.jobit.entity.Resume;
import fit.hcmute.jobit.entity.User;
import fit.hcmute.jobit.exception.IdInvalidException;
import fit.hcmute.jobit.repository.JobRepository;
import fit.hcmute.jobit.repository.ResumeRepository;
import fit.hcmute.jobit.repository.UserRepository;
import fit.hcmute.jobit.service.ResumeService;
import fit.hcmute.jobit.util.SecurityUtil;
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
    private final FilterParser filterParser;
    private final FilterSpecificationConverter filterSpecificationConverter;

    @Override
    public CreateResumeResponse createResume(CreateResumeRequest request) {

        Optional<User> userOptional = userRepository.findById(request.getUser().getId());

        Optional<Job>  jobOptional = jobRepository.findById(request.getJob().getId());

        if (userOptional.isEmpty() || jobOptional.isEmpty()) {
            throw new IdInvalidException("User or Job not found");
        }

        boolean alreadyApplied = resumeRepository.existsByUser_IdAndJob_Id(request.getUser().getId(), request.getJob().getId());
        if (alreadyApplied) {
            throw new IdInvalidException("User has already applied to this job");
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

    @Override
    public ResultPaginationResponse getAllResumesByUser(Pageable pageable) {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : null;

        FilterNode node = filterParser.parse("email='" + email + "'");
        Specification<Resume> specification = filterSpecificationConverter.convert(node);

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

    @Override
    public List<ResumeReponse> getResumesByJobId(Long jobId) {
        Optional<Job>  jobOptional = jobRepository.findById(jobId);
        if (jobOptional.isEmpty()) {
            throw new IdInvalidException("Job not found with id: " + jobId);
        }

        List<Resume> resumes = resumeRepository.findByJob_Id(jobId);
        return resumes.stream().map(resumeMapper::toResumeReponse).toList();

    }
}
