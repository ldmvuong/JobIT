package fit.hcmute.JobIT.service;

import fit.hcmute.JobIT.entity.Company;

import java.util.List;

public interface CompanyService {

    Company createCompany(Company company);

    List<Company> getAllCompanies();

    Company updateCompany(Company company);

    void deleteCompany(Long id);

}
