package fit.hcmute.jobit.converter;

import fit.hcmute.jobit.dto.response.company.CompanyNameResponse;
import fit.hcmute.jobit.dto.response.company.CompanyResponse;
import fit.hcmute.jobit.dto.response.user.CreateUserResponse;
import fit.hcmute.jobit.dto.response.user.UpdateUserResponse;
import fit.hcmute.jobit.dto.response.user.UserResponse;
import fit.hcmute.jobit.entity.Company;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CreateUserResponse.CompanyUser toCompanyUser(Company company);
    UserResponse.CompanyUser toUserResponse(Company company);
    UpdateUserResponse.CompanyUser toUpdateUserResponse(Company company);
    CompanyResponse toCompanyResponse(Company company);
    CompanyNameResponse toCompanyNameResponse(Company company);
}
