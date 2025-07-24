package fit.hcmute.jobit.converter;

import fit.hcmute.jobit.dto.request.role.CreateRoleRequest;
import fit.hcmute.jobit.dto.request.role.UpdateRoleRequest;
import fit.hcmute.jobit.dto.response.role.RoleResponse;
import fit.hcmute.jobit.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true) // xử lý thủ công do chỉ truyền id
    Role toEntity(CreateRoleRequest createRoleRequest);
    Role toEntity(UpdateRoleRequest updateRoleRequest);
    RoleResponse toResponse(Role role);
}
