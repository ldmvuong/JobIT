package fit.hcmute.JobIT.service.impl;

import fit.hcmute.JobIT.converter.RoleMapper;
import fit.hcmute.JobIT.dto.request.IdRequest;
import fit.hcmute.JobIT.dto.request.role.CreateRoleRequest;
import fit.hcmute.JobIT.dto.request.role.UpdateRoleRequest;
import fit.hcmute.JobIT.dto.response.role.RoleResponse;
import fit.hcmute.JobIT.entity.Permission;
import fit.hcmute.JobIT.entity.Role;
import fit.hcmute.JobIT.entity.Skill;
import fit.hcmute.JobIT.exception.IdInvalidException;
import fit.hcmute.JobIT.repository.PermissionRepository;
import fit.hcmute.JobIT.repository.RoleRepository;
import fit.hcmute.JobIT.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse createRole(CreateRoleRequest createRoleRequest) {
        if(roleRepository.existsByName(createRoleRequest.getName())) {
            throw new IdInvalidException("Role with name " + createRoleRequest.getName() + " already exists");
        }

        List<Long> inputPermissionIds = createRoleRequest.getPermissions()
                .stream().map(IdRequest::getId).toList();

        List<Permission> permissions = permissionRepository.findAllById(inputPermissionIds);

        Role role = roleMapper.toEntity(createRoleRequest);
        role.setPermissions(permissions);
        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse updateRole(UpdateRoleRequest updateRoleRequest) {
        Optional<Role> role = roleRepository.findById(updateRoleRequest.getId());

        if(role.isPresent()) {
            if(roleRepository.existsByName(updateRoleRequest.getName()) &&
               !role.get().getName().equals(updateRoleRequest.getName())) {
                throw new IdInvalidException("Role with name " + updateRoleRequest.getName() + " already exists");
            }

            Role updatedRole = roleMapper.toEntity(updateRoleRequest);
            return roleMapper.toResponse(roleRepository.save(updatedRole));
        } else {
            throw new IdInvalidException("Role with ID " + updateRoleRequest.getId() + " not found");
        }
    }
}
