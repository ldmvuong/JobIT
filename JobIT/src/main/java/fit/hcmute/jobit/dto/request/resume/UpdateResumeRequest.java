package fit.hcmute.jobit.dto.request.resume;

import fit.hcmute.jobit.enums.EResumeStatus;
import fit.hcmute.jobit.util.annotation.enumvalidate.subnet.EnumSubset;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateResumeRequest {
    @NotNull(message = "Resume ID is required")
    private Long id;

    @NotNull(message = "Status must not be null")
    @EnumSubset(enumClass = EResumeStatus.class)
    private EResumeStatus status;
}
