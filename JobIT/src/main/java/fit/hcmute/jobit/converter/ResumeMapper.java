package fit.hcmute.jobit.converter;

import fit.hcmute.jobit.dto.request.resume.CreateResumeRequest;
import fit.hcmute.jobit.dto.response.resume.CreateResumeResponse;
import fit.hcmute.jobit.dto.response.resume.ResumeReponse;
import fit.hcmute.jobit.dto.response.resume.UpdateResumeRespone;
import fit.hcmute.jobit.entity.Resume;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResumeMapper {
    CreateResumeResponse toCreateResponse(Resume resume);
    UpdateResumeRespone toUpdateResponse(Resume resume);
    ResumeReponse toResumeReponse(Resume resume);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "job", ignore = true)
    Resume toResume(CreateResumeRequest createResumeRequest);
}
