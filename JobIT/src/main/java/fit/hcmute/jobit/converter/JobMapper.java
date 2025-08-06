package fit.hcmute.jobit.converter;

import fit.hcmute.jobit.dto.request.job.JobRequest;
import fit.hcmute.jobit.dto.response.job.CreateJobResponse;
import fit.hcmute.jobit.dto.response.job.JobResponse;
import fit.hcmute.jobit.dto.response.job.JobSimpleResponse;
import fit.hcmute.jobit.dto.response.job.UpdateJobResponse;
import fit.hcmute.jobit.entity.Job;
import fit.hcmute.jobit.entity.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface JobMapper {

    @Mapping(target = "skills", ignore = true) // xử lý thủ công do chỉ truyền id
    Job toEntity(JobRequest jobRequest);

    // Từ Job → CreateJobResponse
    @Mapping(target = "skills", source = "skills")
    CreateJobResponse toCreateResponse(Job job);

    // Từ Job → UpdateJobResponse
    @Mapping(target = "skills", source = "skills")
    UpdateJobResponse toUpdateResponse(Job job);

    // Từ Job → JobResponse
    @Mapping(target = "skills", source = "skills")
    @Mapping(target = "company", source = "company")
    JobResponse toJobResponse(Job job);

    // Từ Job → JobSimpleResponse
    JobSimpleResponse toJobSimpleResponse(Job job);

    @Mapping(target = "skills", ignore = true) // cập nhật thủ công
    void updateFromRequest(JobRequest request, @MappingTarget Job job);

    default String map(Skill skill) {
        return skill.getName();
    }

}
