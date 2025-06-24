package fit.hcmute.JobIT.converter;

import fit.hcmute.JobIT.dto.response.company.CompanyResponse;
import fit.hcmute.JobIT.dto.response.user.CreateUserResponse;
import fit.hcmute.JobIT.dto.response.user.UpdateUserResponse;
import fit.hcmute.JobIT.dto.response.user.UserResponse;
import fit.hcmute.JobIT.entity.Company;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CreateUserResponse.CompanyUser toCompanyUser(Company company);
    UserResponse.CompanyUser toUserResponse(Company company);
    UpdateUserResponse.CompanyUser toUpdateUserResponse(Company company);
    CompanyResponse toCompanyResponse(Company company);
}
