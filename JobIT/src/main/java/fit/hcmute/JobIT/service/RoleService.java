package fit.hcmute.JobIT.service;

import fit.hcmute.JobIT.dto.request.role.CreateRoleRequest;
import fit.hcmute.JobIT.dto.request.role.UpdateRoleRequest;
import fit.hcmute.JobIT.dto.response.ResultPaginationResponse;
import fit.hcmute.JobIT.dto.response.role.RoleResponse;
import fit.hcmute.JobIT.entity.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface RoleService {
    RoleResponse createRole(CreateRoleRequest createRoleRequest);
    RoleResponse updateRole(UpdateRoleRequest updateRoleRequest);
    void deleteRole(Long id);
    ResultPaginationResponse getAllRoles(Specification<Role> specification, Pageable pageable);
    Role getRoleById(Long id);
}
