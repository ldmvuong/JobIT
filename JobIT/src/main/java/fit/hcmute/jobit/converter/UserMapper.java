package fit.hcmute.jobit.converter;


import fit.hcmute.jobit.dto.response.user.CreateUserResponse;
import fit.hcmute.jobit.dto.response.user.UpdateUserResponse;
import fit.hcmute.jobit.dto.response.user.UserResponse;
import fit.hcmute.jobit.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    CreateUserResponse toCreateUserResponse(User user);
    UpdateUserResponse toUpdateUserResponse(User user);
    UserResponse toUserResponse(User user);
}
