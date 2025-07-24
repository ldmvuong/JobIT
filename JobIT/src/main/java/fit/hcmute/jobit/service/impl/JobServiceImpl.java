package fit.hcmute.jobit.service.impl;

import fit.hcmute.jobit.converter.JobMapper;
import fit.hcmute.jobit.dto.request.job.JobRequest;
import fit.hcmute.jobit.dto.response.ResultPaginationResponse;
import fit.hcmute.jobit.dto.response.job.CreateJobResponse;
import fit.hcmute.jobit.dto.response.job.JobResponse;
import fit.hcmute.jobit.dto.response.job.UpdateJobResponse;
import fit.hcmute.jobit.entity.Job;
import fit.hcmute.jobit.entity.Skill;
import fit.hcmute.jobit.exception.IdInvalidException;
import fit.hcmute.jobit.repository.JobRepository;
import fit.hcmute.jobit.repository.SkillRepository;
import fit.hcmute.jobit.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public JobResponse getJobById(Long id) {
        if (id == null) {
            throw new IdInvalidException("Job ID must not be null");
        }

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Job not found with id: " + id));

        return jobMapper.toJobResponse(job);

    }

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

    @Override
    public ResultPaginationResponse getAllJob(Specification<Job> specification, Pageable pageable) {
        Page<Job> pageJob = jobRepository.findAll(specification, pageable);

        List<JobResponse> jobResponses = pageJob
                .getContent()
                .stream()
                .map(jobMapper::toJobResponse)
                .toList();

        ResultPaginationResponse.Meta meta = new ResultPaginationResponse.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pageJob.getTotalPages());
        meta.setTotal(pageJob.getTotalElements());

        ResultPaginationResponse response = new ResultPaginationResponse();
        response.setMeta(meta);
        response.setResult(jobResponses);
        return response;
    }
}
