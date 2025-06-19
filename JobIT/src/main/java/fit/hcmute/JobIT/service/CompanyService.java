package fit.hcmute.JobIT.service;

import fit.hcmute.JobIT.dto.response.ResultPaginationResponse;
import fit.hcmute.JobIT.entity.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CompanyService {

    Company createCompany(Company company);

    ResultPaginationResponse getAllCompanies(Specification<Company> specification,Pageable pageable);

    Company updateCompany(Company company);

    void deleteCompany(Long id);

}
