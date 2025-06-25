package fit.hcmute.JobIT.service.impl;

import fit.hcmute.JobIT.converter.ResumeMapper;
import fit.hcmute.JobIT.dto.request.resume.CreateResumeRequest;
import fit.hcmute.JobIT.dto.response.resume.CreateResumeResponse;
import fit.hcmute.JobIT.entity.Job;
import fit.hcmute.JobIT.entity.Resume;
import fit.hcmute.JobIT.entity.User;
import fit.hcmute.JobIT.exception.IdInvalidException;
import fit.hcmute.JobIT.repository.JobRepository;
import fit.hcmute.JobIT.repository.ResumeRepository;
import fit.hcmute.JobIT.repository.UserRepository;
import fit.hcmute.JobIT.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
