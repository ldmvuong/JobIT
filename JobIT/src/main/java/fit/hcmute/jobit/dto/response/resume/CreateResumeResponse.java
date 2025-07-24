package fit.hcmute.jobit.dto.response.resume;


import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class CreateResumeResponse {
    private Long id;
    private Instant createdAt;
    private String createdBy;
}
