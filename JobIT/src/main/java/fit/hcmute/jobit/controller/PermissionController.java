package fit.hcmute.jobit.controller;

import com.turkraft.springfilter.boot.Filter;
import fit.hcmute.jobit.dto.request.permission.CreatePermissionRequest;
import fit.hcmute.jobit.dto.request.permission.UpdatePermissionRequest;
import fit.hcmute.jobit.dto.response.ResultPaginationResponse;
import fit.hcmute.jobit.dto.response.permission.PermissionResponse;
import fit.hcmute.jobit.entity.Permission;
import fit.hcmute.jobit.service.PermissionService;
import fit.hcmute.jobit.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;

    @PostMapping
    @ApiMessage("Tạo mới quyền truy cập API")
    public ResponseEntity<PermissionResponse> createPermission(@Valid @RequestBody CreatePermissionRequest createPermissionRequest) {
        return ResponseEntity.ok(permissionService.createPermission(createPermissionRequest));
    }

    @PutMapping
    @ApiMessage("Cập nhật quyền truy cập API")
    public ResponseEntity<PermissionResponse> updatePermission(@Valid @RequestBody UpdatePermissionRequest updatePermissionRequest) {
        return ResponseEntity.ok(permissionService.updatePermission(updatePermissionRequest));
    }

    @GetMapping
    @ApiMessage("Lấy danh sách quyền truy cập API")
    public ResponseEntity<ResultPaginationResponse> getAllPermissions(
            @Filter Specification<Permission> specification,
            Pageable pageable) {
        return ResponseEntity.ok(permissionService.getAllPermissions(specification, pageable));
    }

    @GetMapping("/{id}")
    @ApiMessage("Lấy quyền truy cập API theo ID")
    public ResponseEntity<PermissionResponse> getPermissionById(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.getPermissionById(id));
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Xoá quyền truy cập API theo ID")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.ok().build();
    }
}
