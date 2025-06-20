package fit.hcmute.JobIT.service.impl;

import fit.hcmute.JobIT.converter.UserMapper;
import fit.hcmute.JobIT.dto.response.*;
import fit.hcmute.JobIT.entity.User;
import fit.hcmute.JobIT.exception.IdInvalidException;
import fit.hcmute.JobIT.repository.UserRepository;
import fit.hcmute.JobIT.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CreateUserResponse createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IdInvalidException("Email already exists: " + user.getEmail() + ". Please use a different email.");
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
        Meta meta = new Meta();

        meta.setPage(pageUser.getNumber() + 1); // Page numbers are 0-based in Spring Data
        meta.setPageSize(pageUser.getSize());
        meta.setPages(pageUser.getTotalPages());
        meta.setTotal(pageUser.getTotalElements());

        List<UserResponse> userResponses = pageUser.getContent().stream().map(userMapper::toUserResponse).toList();

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
}
