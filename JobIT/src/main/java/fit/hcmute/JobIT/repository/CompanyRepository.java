package fit.hcmute.JobIT.repository;

import fit.hcmute.JobIT.entity.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    // Define any custom query methods if needed
}
