package fit.hcmute.jobit.service.impl;

import fit.hcmute.jobit.converter.UserMapper;
import fit.hcmute.jobit.dto.response.*;
import fit.hcmute.jobit.dto.response.user.CreateUserResponse;
import fit.hcmute.jobit.dto.response.user.UpdateUserResponse;
import fit.hcmute.jobit.dto.response.user.UserResponse;
import fit.hcmute.jobit.entity.Company;
import fit.hcmute.jobit.entity.Role;
import fit.hcmute.jobit.entity.User;
import fit.hcmute.jobit.exception.IdInvalidException;
import fit.hcmute.jobit.repository.UserRepository;
import fit.hcmute.jobit.service.CompanyService;
import fit.hcmute.jobit.service.RoleService;
import fit.hcmute.jobit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CompanyService companyService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public CreateUserResponse createUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IdInvalidException("Email already exists: " + user.getEmail() + ". Please use a different email.");
        }

        if (user.getCompany() != null) {
            Optional<Company> companyOptional = companyService.findById(user.getCompany().getId());
            user.setCompany(companyOptional.orElse(null));
        }

        if (user.getRole() != null) {
            Role role = roleService.getRoleById(user.getRole().getId());
            user.setRole(role);
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        User savedUser = userRepository.save(user);
        return userMapper.toCreateUserResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("User not found with ID: " + id));
        return userMapper.toUserResponse(user);
    }

    @Override
    public ResultPaginationResponse getAllUsers(Specification<User> specification, Pageable pageable) {
        Page<User> pageUser = userRepository.findAll(specification, pageable);

        ResultPaginationResponse resultPaginationResponse = new ResultPaginationResponse();
        ResultPaginationResponse.Meta meta = new ResultPaginationResponse.Meta();

        meta.setPage(pageUser.getNumber() + 1); // Page numbers are 0-based in Spring Data
        meta.setPageSize(pageUser.getSize());
        meta.setPages(pageUser.getTotalPages());
        meta.setTotal(pageUser.getTotalElements());

        List<UserResponse> userResponses = pageUser
                .getContent()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();

        resultPaginationResponse.setMeta(meta);
        resultPaginationResponse.setResult(userResponses);

        return resultPaginationResponse;
    }

    @Override
    public UpdateUserResponse updateUser(User user) {

        User userCurrent = userRepository.findById(user.getId())
                .orElseThrow(() -> new IdInvalidException("User not found with ID: " + user.getId()));

        userCurrent.setName(user.getName());
        userCurrent.setGender(user.getGender());
        userCurrent.setAddress(user.getAddress());
        userCurrent.setAge(user.getAge());

        if (user.getCompany() != null) {
            Optional<Company> companyOptional = companyService.findById(user.getCompany().getId());
            userCurrent.setCompany(companyOptional.orElse(null));
        }

        if (user.getRole() != null) {
            Role role = roleService.getRoleById(user.getRole().getId());
            userCurrent.setRole(role);
        }

        User updatedUser = userRepository.save(userCurrent);

        return userMapper.toUpdateUserResponse(updatedUser);

    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IdInvalidException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void updateUserToken(String email, String token) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setRefreshToken(token);
            userRepository.save(user);
        } else {
            throw new IdInvalidException("User not found with email: " + email);
        }
    }

    @Override
    public User findByRefreshTokenAndEmail(String refreshToken, String email) {
        User user = userRepository.findByRefreshTokenAndEmail(refreshToken, email);
        if (user != null) {
            return user;
        } else {
            throw new IdInvalidException("Refresh token or email is invalid.");
        }
    }
}
