package fit.hcmute.JobIT.service;

import fit.hcmute.JobIT.dto.response.user.CreateUserResponse;
import fit.hcmute.JobIT.dto.response.ResultPaginationResponse;
import fit.hcmute.JobIT.dto.response.user.UpdateUserResponse;
import fit.hcmute.JobIT.dto.response.user.UserResponse;
import fit.hcmute.JobIT.entity.User;
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

    void updateUserToken(String email,String token);

    User findByRefreshTokenAndEmail(String refreshToken, String email);

}
