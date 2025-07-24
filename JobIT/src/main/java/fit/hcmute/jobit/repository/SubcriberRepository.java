package fit.hcmute.jobit.repository;

import fit.hcmute.jobit.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcriberRepository extends JpaRepository<Subscriber, Long> {
    boolean existsByEmail(String email);
    Subscriber findByEmail(String email);
}
