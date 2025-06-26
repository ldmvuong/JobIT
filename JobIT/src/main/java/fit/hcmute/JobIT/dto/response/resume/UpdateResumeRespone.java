package fit.hcmute.JobIT.dto.response.resume;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class UpdateResumeRespone {
    private Instant updatedAt;
    private String updatedBy;
}
