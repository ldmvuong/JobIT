package fit.hcmute.JobIT.converter;

import fit.hcmute.JobIT.dto.request.permission.CreatePermissionRequest;
import fit.hcmute.JobIT.dto.request.permission.UpdatePermissionRequest;
import fit.hcmute.JobIT.dto.response.permission.PermissionResponse;
import fit.hcmute.JobIT.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermissionEntity(CreatePermissionRequest createPermissionRequest);
    Permission toPermissionEntity(UpdatePermissionRequest updatePermissionRequest);
    PermissionResponse toPermissionResponse(Permission permission);
}
