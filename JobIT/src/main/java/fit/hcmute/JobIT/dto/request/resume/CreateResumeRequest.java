package fit.hcmute.JobIT.dto.request.resume;

import fit.hcmute.JobIT.dto.request.job.JobIdRequest;
import fit.hcmute.JobIT.dto.request.user.UserIdRequest;
import fit.hcmute.JobIT.enums.EResumeStatus;
import fit.hcmute.JobIT.util.annotation.enumvalidate.subnet.EnumSubset;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class CreateResumeRequest {
    @Email
    private String email;
    private String url;

    @EnumSubset(enumClass = EResumeStatus.class)
    private EResumeStatus status;

    @NotNull(message = "User must not be null")
    @Valid
    private UserIdRequest user;

    @NotNull(message = "Job must not be null")
    @Valid
    private JobIdRequest job;
}
