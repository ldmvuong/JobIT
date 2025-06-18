package fit.hcmute.JobIT.repository;

import fit.hcmute.JobIT.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    Page<User> findAll(Pageable pageable);

}
