package fit.hcmute.JobIT.service.impl;

import fit.hcmute.JobIT.dto.response.Meta;
import fit.hcmute.JobIT.dto.response.ResultPaginationResponse;
import fit.hcmute.JobIT.entity.Company;
import fit.hcmute.JobIT.repository.CompanyRepository;
import fit.hcmute.JobIT.service.CompanyService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public ResultPaginationResponse getAllCompanies(Specification<Company> specification, Pageable pageable) {
        Page<Company> pageCompany = companyRepository.findAll(specification,pageable);

        ResultPaginationResponse resultPaginationResponse = new ResultPaginationResponse();
        Meta meta = new Meta();

        meta.setPage(pageable.getPageNumber() + 1); // Page numbers are 0-based in Spring Data
        meta.setPageSize(pageable.getPageSize());

        meta.setPages(pageCompany.getTotalPages());
        meta.setTotal(pageCompany.getTotalElements());

        resultPaginationResponse.setMeta(meta);
        resultPaginationResponse.setResult(pageCompany.getContent());

        return resultPaginationResponse;
    }

    @Override
    public Company updateCompany(Company company) {
        Optional<Company> companyOptional = companyRepository.findById(company.getId());
        if (companyOptional.isPresent()) {
            Company existingCompany = companyOptional.get();
            existingCompany.setName(company.getName());
            existingCompany.setDescription(company.getDescription());
            existingCompany.setAddress(company.getAddress());
            return companyRepository.save(existingCompany);
        }
        return null;
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
