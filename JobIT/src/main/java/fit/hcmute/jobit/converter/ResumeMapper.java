package fit.hcmute.jobit.converter;

import fit.hcmute.jobit.dto.request.resume.CreateResumeRequest;
import fit.hcmute.jobit.dto.response.resume.CreateResumeResponse;
import fit.hcmute.jobit.dto.response.resume.ResumeReponse;
import fit.hcmute.jobit.dto.response.resume.UpdateResumeRespone;
import fit.hcmute.jobit.entity.Resume;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {JobMapper.class, UserMapper.class})
public interface ResumeMapper {
    CreateResumeResponse toCreateResponse(Resume resume);
    UpdateResumeRespone toUpdateResponse(Resume resume);
    
    @Mapping(target = "company", source = "job.company")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "job", source = "job")
    ResumeReponse toResumeReponse(Resume resume);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "job", ignore = true)
    Resume toResume(CreateResumeRequest createResumeRequest);
}
