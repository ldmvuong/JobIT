package fit.hcmute.JobIT.service;

import fit.hcmute.JobIT.dto.response.ResultPaginationResponse;
import fit.hcmute.JobIT.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUserById(Long id);

    ResultPaginationResponse getAllUsers(Pageable pageable);

    User updateUser(User user);

    void deleteUser(Long id);

    User getUserByEmail(String email);
}
