package fit.hcmute.jobit.service;

import fit.hcmute.jobit.dto.response.user.CreateUserResponse;
import fit.hcmute.jobit.dto.response.ResultPaginationResponse;
import fit.hcmute.jobit.dto.response.user.UpdateUserResponse;
import fit.hcmute.jobit.dto.response.user.UserResponse;
import fit.hcmute.jobit.entity.User;
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
