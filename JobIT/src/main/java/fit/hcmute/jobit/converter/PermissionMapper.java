package fit.hcmute.jobit.converter;

import fit.hcmute.jobit.dto.request.permission.CreatePermissionRequest;
import fit.hcmute.jobit.dto.request.permission.UpdatePermissionRequest;
import fit.hcmute.jobit.dto.response.permission.PermissionResponse;
import fit.hcmute.jobit.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermissionEntity(CreatePermissionRequest createPermissionRequest);
    Permission toPermissionEntity(UpdatePermissionRequest updatePermissionRequest);
    PermissionResponse toPermissionResponse(Permission permission);
}
