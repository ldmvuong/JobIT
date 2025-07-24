package fit.hcmute.jobit.service;

import fit.hcmute.jobit.dto.request.permission.CreatePermissionRequest;
import fit.hcmute.jobit.dto.request.permission.UpdatePermissionRequest;
import fit.hcmute.jobit.dto.response.ResultPaginationResponse;
import fit.hcmute.jobit.dto.response.permission.PermissionResponse;
import fit.hcmute.jobit.entity.Permission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PermissionService {
    PermissionResponse createPermission(CreatePermissionRequest createPermissionRequest);
    PermissionResponse updatePermission(UpdatePermissionRequest updatePermissionRequest);
    ResultPaginationResponse getAllPermissions(Specification<Permission> specification, Pageable pageable);
    PermissionResponse getPermissionById(Long id);
    void deletePermission(Long id);

}
