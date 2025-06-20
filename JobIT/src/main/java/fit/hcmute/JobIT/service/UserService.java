package fit.hcmute.JobIT.service;

import fit.hcmute.JobIT.dto.response.CreateUserResponse;
import fit.hcmute.JobIT.dto.response.ResultPaginationResponse;
import fit.hcmute.JobIT.dto.response.UpdateUserResponse;
import fit.hcmute.JobIT.dto.response.UserResponse;
import fit.hcmute.JobIT.entity.User;
import fit.hcmute.JobIT.exception.IdInvalidException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserService {
    CreateUserResponse createUser(User user);

    UserResponse getUserById(Long id);

    ResultPaginationResponse getAllUsers(Specification<User> specification, Pageable pageable);

    UpdateUserResponse updateUser(User user);

    void deleteUser(Long id);

    User getUserByEmail(String email);

    boolean existsByEmail(String email);
}
