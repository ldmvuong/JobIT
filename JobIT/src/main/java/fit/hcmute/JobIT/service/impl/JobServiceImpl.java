package fit.hcmute.JobIT.service.impl;

import fit.hcmute.JobIT.converter.JobMapper;
import fit.hcmute.JobIT.dto.request.JobRequest;
import fit.hcmute.JobIT.dto.response.job.CreateJobResponse;
import fit.hcmute.JobIT.dto.response.job.UpdateJobResponse;
import fit.hcmute.JobIT.entity.Job;
import fit.hcmute.JobIT.entity.Skill;
import fit.hcmute.JobIT.exception.IdInvalidException;
import fit.hcmute.JobIT.repository.JobRepository;
import fit.hcmute.JobIT.repository.SkillRepository;
import fit.hcmute.JobIT.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;
    private final JobMapper jobMapper;

    @Override
    public CreateJobResponse createJob(JobRequest jobRequest) {
        Job job = jobMapper.toEntity(jobRequest);

        List<Long> skillIds = Optional.ofNullable(jobRequest.getSkills())
                .orElse(Collections.emptyList())
                .stream()
                .map(JobRequest.SkillId::getId)
                .toList();

        List<Skill> skills = skillRepository.findByIdIn(skillIds);
        job.setSkills(skills);

        return jobMapper.toCreateResponse(jobRepository.save(job));
    }

    @Override
    public UpdateJobResponse updateJob(JobRequest jobRequest) {
        if (jobRequest.getId() == null) {
            throw new IdInvalidException("Job ID must not be null");
        }

        Job existingJob = jobRepository.findById(jobRequest.getId())
                .orElseThrow(() -> new IdInvalidException("Job not found with id: " + jobRequest.getId()));

        // Cập nhật từ DTO sang entity đang tồn tại
        jobMapper.updateFromRequest(jobRequest, existingJob);

        // Xử lý danh sách skillId (nếu có)
        List<Long> skillIds = Optional.ofNullable(jobRequest.getSkills())
                .orElse(Collections.emptyList())
                .stream()
                .map(JobRequest.SkillId::getId)
                .toList();

        List<Skill> skills = skillRepository.findByIdIn(skillIds);
        existingJob.setSkills(skills);

        Job updatedJob = jobRepository.save(existingJob);
        return jobMapper.toUpdateResponse(updatedJob);
    }

    @Override
    public void deleteJob(Long id) {
        Optional<Job> job = jobRepository.findById(id);
        if (job.isEmpty()) {
            throw new IdInvalidException("Job not found with id: " + id);
        }
        jobRepository.deleteById(id);
    }
}
