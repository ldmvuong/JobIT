package fit.hcmute.JobIT.dto.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class UserIdRequest {

    @NotNull(message = "User ID is required")
    private Long id;
}
