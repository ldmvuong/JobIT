package fit.hcmute.jobit.service.impl;

import fit.hcmute.jobit.converter.CompanyMapper;
import fit.hcmute.jobit.dto.response.company.CompanyResponse;
import fit.hcmute.jobit.dto.response.ResultPaginationResponse;
import fit.hcmute.jobit.dto.response.user.UserResponse;
import fit.hcmute.jobit.entity.Company;
import fit.hcmute.jobit.entity.Job;
import fit.hcmute.jobit.entity.User;
import fit.hcmute.jobit.exception.IdInvalidException;
import fit.hcmute.jobit.repository.CompanyRepository;
import fit.hcmute.jobit.repository.UserRepository;
import fit.hcmute.jobit.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CompanyMapper companyMapper;


    @Override
    public Optional<Company> findById(long id) {
        return companyRepository.findById(id);
    }

    @Override
    public CompanyResponse getCompanyById(Long id) {
        if (id == null) {
            throw new IdInvalidException("Company ID must not be null");
        }

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IdInvalidException("Company not found with id: " + id));

        return companyMapper.toCompanyResponse(company);
    }

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public ResultPaginationResponse getAllCompanies(Specification<Company> specification, Pageable pageable) {
        Page<Company> pageCompany = companyRepository.findAll(specification, pageable);

        List<CompanyResponse> companyResponses = pageCompany
                .getContent()
                .stream()
                .map(companyMapper::toCompanyResponse)
                .toList();

        ResultPaginationResponse.Meta meta = new ResultPaginationResponse.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pageCompany.getTotalPages());
        meta.setTotal(pageCompany.getTotalElements());

        ResultPaginationResponse response = new ResultPaginationResponse();
        response.setMeta(meta);
        response.setResult(companyResponses);

        return response;
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
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isEmpty()) {
            throw new IdInvalidException("Company not found with ID: " + id);
        } else {
            List<User> users = companyOptional.get().getUsers();
            userRepository.deleteAll(users);
            companyRepository.deleteById(id);
        }
    }

    @Override
    public ResultPaginationResponse getHrsByCompanyId(Long id) {
        return null;
    }


}
