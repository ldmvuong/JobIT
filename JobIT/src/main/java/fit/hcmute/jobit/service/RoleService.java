package fit.hcmute.jobit.service;

import fit.hcmute.jobit.dto.request.role.CreateRoleRequest;
import fit.hcmute.jobit.dto.request.role.UpdateRoleRequest;
import fit.hcmute.jobit.dto.response.ResultPaginationResponse;
import fit.hcmute.jobit.dto.response.role.RoleResponse;
import fit.hcmute.jobit.entity.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface RoleService {
    RoleResponse createRole(CreateRoleRequest createRoleRequest);
    RoleResponse updateRole(UpdateRoleRequest updateRoleRequest);
    void deleteRole(Long id);
    ResultPaginationResponse getAllRoles(Specification<Role> specification, Pageable pageable);
    Role getRoleById(Long id);
}
