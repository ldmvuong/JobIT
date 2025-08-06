package fit.hcmute.jobit.controller;

import com.turkraft.springfilter.boot.Filter;
import fit.hcmute.jobit.dto.response.ResultPaginationResponse;
import fit.hcmute.jobit.dto.response.company.CompanyResponse;
import fit.hcmute.jobit.dto.response.job.JobResponse;
import fit.hcmute.jobit.dto.response.user.UserResponse;
import fit.hcmute.jobit.entity.Company;
import fit.hcmute.jobit.service.CompanyService;
import fit.hcmute.jobit.service.JobService;
import fit.hcmute.jobit.service.UserService;
import fit.hcmute.jobit.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final UserService userService;
    private final JobService jobService;

    @PostMapping
    @ApiMessage("Create a new company")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody Company reqCompany) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(reqCompany));
    }

    @GetMapping
    @ApiMessage("Get all companies")
    public ResponseEntity<ResultPaginationResponse> getAllCompanies(
            @Filter Specification<Company> specification,
            Pageable pageable
    ) {
        return ResponseEntity.ok(companyService.getAllCompanies(specification, pageable));
    }

    @PutMapping
    @ApiMessage("Update an existing company")
    public ResponseEntity<Company> updateCompany(@Valid @RequestBody Company reqCompany) {
        Company updatedCompany = companyService.updateCompany(reqCompany);
        return ResponseEntity.ok(updatedCompany);
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete a company by ID")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}")
    @ApiMessage("Get a company by ID")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @GetMapping("/{id}/hrs")
    @ApiMessage("Get HR users by company ID")
    public ResponseEntity<List<UserResponse>> getHrUsersByCompany(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findHrUsersByCompany(id));
    }

    @GetMapping("/{id}/jobs")
    @ApiMessage("Get all jobs by company ID")
    public ResponseEntity<List<JobResponse>> getAllJobsByCompany(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.findJobsByCompany(id));
    }
}
