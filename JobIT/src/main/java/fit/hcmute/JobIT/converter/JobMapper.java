package fit.hcmute.JobIT.converter;

import fit.hcmute.JobIT.dto.request.JobRequest;
import fit.hcmute.JobIT.dto.response.job.CreateJobResponse;
import fit.hcmute.JobIT.dto.response.job.UpdateJobResponse;
import fit.hcmute.JobIT.entity.Job;
import fit.hcmute.JobIT.entity.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface JobMapper {
    @Mapping(target = "skills", ignore = true) // xử lý thủ công do chỉ truyền id
    Job toEntity(JobRequest jobRequest);

    // Từ Job → CreateJobResponse
    @Mapping(target = "skills", source = "skills")
    CreateJobResponse toCreateResponse(Job job);

    // Từ Job → UpdateJobResponse
    @Mapping(target = "skills", source = "skills")
    UpdateJobResponse toUpdateResponse(Job job);

    @Mapping(target = "skills", ignore = true) // cập nhật thủ công
    void updateFromRequest(JobRequest request, @MappingTarget Job job);

    default String map(Skill skill) {
        return skill.getName();
    }

}
