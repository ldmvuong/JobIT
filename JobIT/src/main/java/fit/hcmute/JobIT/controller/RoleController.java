package fit.hcmute.JobIT.controller;

import fit.hcmute.JobIT.dto.request.role.CreateRoleRequest;
import fit.hcmute.JobIT.dto.response.role.RoleResponse;
import fit.hcmute.JobIT.service.RoleService;
import fit.hcmute.JobIT.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
