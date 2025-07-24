package fit.hcmute.jobit.dto.response.role;

import fit.hcmute.jobit.dto.response.permission.PermissionResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleResponse {
    private Long id;
    private String name;
    private String description;
    private boolean active;
    private List<PermissionResponse> permissions;
}
