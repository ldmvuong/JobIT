package fit.hcmute.jobit.controller;

import com.turkraft.springfilter.boot.Filter;
import fit.hcmute.jobit.dto.response.user.CreateUserResponse;
import fit.hcmute.jobit.dto.response.ResultPaginationResponse;
import fit.hcmute.jobit.dto.response.user.UpdateUserResponse;
import fit.hcmute.jobit.dto.response.user.UserResponse;
import fit.hcmute.jobit.entity.User;
import fit.hcmute.jobit.service.UserService;
import fit.hcmute.jobit.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    @ApiMessage("Get user by ID")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @GetMapping("/users")
    @ApiMessage("Get all users with pagination and filtering")
    public ResponseEntity<ResultPaginationResponse> getAllUsers(
            @Filter Specification<User> specification,
            Pageable pageable
    ) {
        return ResponseEntity.ok(userService.getAllUsers(specification, pageable));
    }

    @PostMapping("/users")
    @ApiMessage("Create a new user")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody User reqUser) {
        CreateUserResponse createdUser = userService.createUser(reqUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/users")
    @ApiMessage("Update an existing user")
    public ResponseEntity<UpdateUserResponse> updateUser(@RequestBody User reqUser) {
        return ResponseEntity.ok(userService.updateUser(reqUser));
    }

    @DeleteMapping("/users/{id}")
    @ApiMessage("Delete a user")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(null);
    }
}
