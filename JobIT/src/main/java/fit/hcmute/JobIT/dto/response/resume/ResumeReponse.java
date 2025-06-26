package fit.hcmute.JobIT.dto.response.resume;

import fit.hcmute.JobIT.dto.response.company.CompanyNameResponse;
import fit.hcmute.JobIT.dto.response.job.JobSimpleResponse;
import fit.hcmute.JobIT.dto.response.user.UserSimpleResponse;
import fit.hcmute.JobIT.enums.EResumeStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ResumeReponse {
    private Long id;
    private String email;
    private String url;
    private EResumeStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
    private CompanyNameResponse company;
    private UserSimpleResponse user;
    private JobSimpleResponse job;
}
