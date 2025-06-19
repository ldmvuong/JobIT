package fit.hcmute.JobIT.service.impl;

import fit.hcmute.JobIT.dto.response.Meta;
import fit.hcmute.JobIT.dto.response.ResultPaginationResponse;
import fit.hcmute.JobIT.entity.User;
import fit.hcmute.JobIT.repository.UserRepository;
import fit.hcmute.JobIT.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
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

        resultPaginationResponse.setMeta(meta);
        resultPaginationResponse.setResult(pageUser.getContent());

        return resultPaginationResponse;
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
