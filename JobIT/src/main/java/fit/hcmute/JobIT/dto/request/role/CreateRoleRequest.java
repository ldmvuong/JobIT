package fit.hcmute.JobIT.dto.request.role;

import fit.hcmute.JobIT.dto.request.IdRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateRoleRequest {
    private String name;
    private String description;
    private boolean active;
    private List<IdRequest> permissions;
}
