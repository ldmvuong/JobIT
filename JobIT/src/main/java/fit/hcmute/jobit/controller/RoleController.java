package fit.hcmute.jobit.controller;

import com.turkraft.springfilter.boot.Filter;
import fit.hcmute.jobit.dto.request.role.CreateRoleRequest;
import fit.hcmute.jobit.dto.request.role.UpdateRoleRequest;
import fit.hcmute.jobit.dto.response.ResultPaginationResponse;
import fit.hcmute.jobit.dto.response.role.RoleResponse;
import fit.hcmute.jobit.entity.Role;
import fit.hcmute.jobit.service.RoleService;
import fit.hcmute.jobit.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    @ApiMessage("Create a new role")
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody CreateRoleRequest createRoleRequest) {
        RoleResponse roleResponse = roleService.createRole(createRoleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(roleResponse);
    }

    @PutMapping
    @ApiMessage("Update an existing role")
    public ResponseEntity<RoleResponse> updateRole(@Valid @RequestBody UpdateRoleRequest updateRoleRequest) {
        RoleResponse roleResponse = roleService.updateRole(updateRoleRequest);
        return ResponseEntity.ok(roleResponse);
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete a role by ID")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @ApiMessage("Get all roles")
    public ResponseEntity<ResultPaginationResponse> getAllRoles(
            @Filter Specification<Role> specification,
            Pageable pageable) {
        ResultPaginationResponse result = roleService.getAllRoles(specification, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @ApiMessage("Get a role by ID")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Role role = roleService.getRoleById(id);
        return ResponseEntity.ok(role);
    }
}
