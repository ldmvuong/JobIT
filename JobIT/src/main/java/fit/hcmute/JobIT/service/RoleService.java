package fit.hcmute.JobIT.service;

import fit.hcmute.JobIT.dto.request.role.CreateRoleRequest;
import fit.hcmute.JobIT.dto.request.role.UpdateRoleRequest;
import fit.hcmute.JobIT.dto.response.role.RoleResponse;

public interface RoleService {
    RoleResponse createRole(CreateRoleRequest createRoleRequest);
    RoleResponse updateRole(UpdateRoleRequest updateRoleRequest);
}
