package fit.hcmute.JobIT.service.impl;

import fit.hcmute.JobIT.converter.PermissionMapper;
import fit.hcmute.JobIT.dto.request.permission.CreatePermissionRequest;
import fit.hcmute.JobIT.dto.request.permission.UpdatePermissionRequest;
import fit.hcmute.JobIT.dto.response.ResultPaginationResponse;
import fit.hcmute.JobIT.dto.response.permission.PermissionResponse;
import fit.hcmute.JobIT.entity.Permission;
import fit.hcmute.JobIT.exception.IdInvalidException;
import fit.hcmute.JobIT.repository.PermissionRepository;
import fit.hcmute.JobIT.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Override
    public PermissionResponse createPermission(CreatePermissionRequest createPermissionRequest) {

        if(permissionRepository.existsByApiPathAndMethodAndModule(
                createPermissionRequest.getApiPath(),
                createPermissionRequest.getMethod(),
                createPermissionRequest.getModule())) {
            throw new IdInvalidException("Permission already exists");
        }

        Permission permission =  permissionMapper.toPermissionEntity(createPermissionRequest);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    @Override
    public PermissionResponse updatePermission(UpdatePermissionRequest updatePermissionRequest) {

        if(!permissionRepository.existsById(updatePermissionRequest.getId())) {
            throw new IdInvalidException("Permission not found");
        }

        if(permissionRepository.existsByApiPathAndMethodAndModule(
                updatePermissionRequest.getApiPath(),
                updatePermissionRequest.getMethod(),
                updatePermissionRequest.getModule())) {
            throw new IdInvalidException("Permission already exists");
        }

        Permission permission =  permissionMapper.toPermissionEntity(updatePermissionRequest);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));

    }

    @Override
    public ResultPaginationResponse getAllPermissions(Specification<Permission> specification, Pageable pageable) {
        Page<Permission> permissions = permissionRepository.findAll(specification, pageable);

        List<PermissionResponse> permissionResponses = permissions.stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();

        ResultPaginationResponse.Meta meta = new ResultPaginationResponse.Meta();
        meta.setPage(pageable.getPageNumber());
        meta.setPageSize(pageable.getPageSize());
        meta.setTotal(permissions.getTotalElements());
        meta.setPages(permissions.getTotalPages());

        ResultPaginationResponse response = new ResultPaginationResponse();
        response.setResult(permissionResponses);
        response.setMeta(meta);

        return response;
    }

    @Override
    public PermissionResponse getPermissionById(Long id) {
        Optional<Permission> permissionOptional = permissionRepository.findById(id);
        return permissionOptional.map(permissionMapper::toPermissionResponse).orElse(null);
    }

    @Override
    public void deletePermission(Long id) {
        Optional<Permission> permissionOptional = permissionRepository.findById(id);
        if (permissionOptional.isEmpty()) {
            throw new IdInvalidException("Permission not found");
        }

        Permission permission = permissionOptional.get();

        permission.getRoles().forEach(role -> role.getPermissions().remove(permission));

        permissionRepository.deleteById(id);
    }
}
