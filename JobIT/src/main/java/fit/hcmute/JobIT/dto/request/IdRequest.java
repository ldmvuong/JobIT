package fit.hcmute.JobIT.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class IdRequest {

    @NotEmpty(message = "Id must not be empty")
    private Long id;
}
