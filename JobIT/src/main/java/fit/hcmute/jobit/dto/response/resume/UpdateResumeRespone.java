package fit.hcmute.jobit.dto.response.resume;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class UpdateResumeRespone {
    private Instant updatedAt;
    private String updatedBy;
}
