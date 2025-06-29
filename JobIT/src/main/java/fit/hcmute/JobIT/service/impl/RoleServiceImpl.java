package fit.hcmute.JobIT.service.impl;

import fit.hcmute.JobIT.converter.RoleMapper;
import fit.hcmute.JobIT.dto.request.IdRequest;
import fit.hcmute.JobIT.dto.request.role.CreateRoleRequest;
import fit.hcmute.JobIT.dto.request.role.UpdateRoleRequest;
import fit.hcmute.JobIT.dto.response.ResultPaginationResponse;
import fit.hcmute.JobIT.dto.response.permission.PermissionResponse;
import fit.hcmute.JobIT.dto.response.role.RoleResponse;
import fit.hcmute.JobIT.entity.Permission;
import fit.hcmute.JobIT.entity.Role;
import fit.hcmute.JobIT.exception.IdInvalidException;
import fit.hcmute.JobIT.repository.PermissionRepository;
import fit.hcmute.JobIT.repository.RoleRepository;
import fit.hcmute.JobIT.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    public RoleResponse createRole(CreateRoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new IdInvalidException("Role with name " + request.getName() + " already exists");
        }

        Role role = roleMapper.toEntity(request);

        List<IdRequest> permissionRequests = request.getPermissions();
        if (permissionRequests != null && !permissionRequests.isEmpty()) {
            List<Long> ids = permissionRequests.stream().map(IdRequest::getId).toList();
            List<Permission> permissions = permissionRepository.findAllById(ids);
            role.setPermissions(permissions);
        }

        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse updateRole(UpdateRoleRequest request) {
        Role role = roleRepository.findById(request.getId())
                .orElseThrow(() -> new IdInvalidException("Role with ID " + request.getId() + " not found"));

        // Kiểm tra trùng tên nếu tên mới khác tên cũ
        if (!role.getName().equals(request.getName()) &&
                roleRepository.existsByName(request.getName())) {
            throw new IdInvalidException("Role with name " + request.getName() + " already exists");
        }

        Role updatedRole = roleMapper.toEntity(request);

        List<IdRequest> permissionRequests = request.getPermissions();
        if (permissionRequests != null && !permissionRequests.isEmpty()) {
            List<Long> ids = permissionRequests.stream().map(IdRequest::getId).toList();
            List<Permission> permissions = permissionRepository.findAllById(ids);
            updatedRole.setPermissions(permissions);
        }

        return roleMapper.toResponse(roleRepository.save(updatedRole));
    }

    @Override
    public void deleteRole(Long id) {

        boolean exists = roleRepository.existsById(id);
        if (!exists) {
            throw new IdInvalidException("Role with ID " + id + " not found");
        }

        roleRepository.deleteById(id);
    }

    @Override
    public ResultPaginationResponse getAllRoles(Specification<Role> specification, Pageable pageable) {
        Page<Role> roles = roleRepository.findAll(specification, pageable);

        List<RoleResponse> permissionResponses = roles.stream()
                .map(roleMapper::toResponse)
                .toList();

        ResultPaginationResponse.Meta meta = new ResultPaginationResponse.Meta();
        meta.setPage(pageable.getPageNumber());
        meta.setPageSize(pageable.getPageSize());
        meta.setTotal(roles.getTotalElements());
        meta.setPages(roles.getTotalPages());

        ResultPaginationResponse response = new ResultPaginationResponse();
        response.setResult(permissionResponses);
        response.setMeta(meta);

        return response;
    }
}
