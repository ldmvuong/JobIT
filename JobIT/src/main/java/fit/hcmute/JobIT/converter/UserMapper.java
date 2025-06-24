package fit.hcmute.JobIT.converter;


import fit.hcmute.JobIT.dto.response.user.CreateUserResponse;
import fit.hcmute.JobIT.dto.response.user.UpdateUserResponse;
import fit.hcmute.JobIT.dto.response.user.UserResponse;
import fit.hcmute.JobIT.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    CreateUserResponse toCreateUserResponse(User user);
    UpdateUserResponse toUpdateUserResponse(User user);
    UserResponse toUserResponse(User user);
}
