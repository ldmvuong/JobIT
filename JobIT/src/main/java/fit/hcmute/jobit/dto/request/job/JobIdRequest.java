package fit.hcmute.jobit.dto.request.job;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class JobIdRequest {

    @NotNull(message = "Job ID is required")
    private Long id;
}
