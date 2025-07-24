package fit.hcmute.jobit.dto.request.permission;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreatePermissionRequest {
    @NotBlank(message = "Permission name must not be blank")
    private String name;

    @NotBlank(message = "API path must not be blank")
    private String apiPath;

    @NotBlank(message = "HTTP method must not be blank")
    private String method;

    @NotBlank(message = "Module must not be blank")
    private String module;
}
