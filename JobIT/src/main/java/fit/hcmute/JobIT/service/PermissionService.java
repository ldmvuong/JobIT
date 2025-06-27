package fit.hcmute.JobIT.service;

import fit.hcmute.JobIT.dto.request.permission.CreatePermissionRequest;
import fit.hcmute.JobIT.dto.request.permission.UpdatePermissionRequest;
import fit.hcmute.JobIT.dto.response.ResultPaginationResponse;
import fit.hcmute.JobIT.dto.response.permission.PermissionResponse;
import fit.hcmute.JobIT.entity.Permission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PermissionService {
    PermissionResponse createPermission(CreatePermissionRequest createPermissionRequest);
    PermissionResponse updatePermission(UpdatePermissionRequest updatePermissionRequest);
    ResultPaginationResponse getAllPermissions(Specification<Permission> specification, Pageable pageable);

}
