package fit.hcmute.jobit.dto.response.permission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionResponse {
    private Long id;
    private String name;
    private String apiPath;
    private String method;
    private String module;
}
