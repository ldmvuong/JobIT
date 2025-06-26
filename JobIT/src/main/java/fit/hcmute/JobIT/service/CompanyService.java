package fit.hcmute.JobIT.service;

import fit.hcmute.JobIT.dto.response.ResultPaginationResponse;
import fit.hcmute.JobIT.dto.response.company.CompanyResponse;
import fit.hcmute.JobIT.entity.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface CompanyService {

    Optional<Company> findById(long id);

    CompanyResponse getCompanyById(Long id);

    Company createCompany(Company company);

    ResultPaginationResponse getAllCompanies(Specification<Company> specification,Pageable pageable);

    Company updateCompany(Company company);

    void deleteCompany(Long id);
}
