package fit.hcmute.jobit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import fit.hcmute.jobit.entity.Role;
import lombok.*;

@Getter
@Setter
public class LoginResponse {

    @JsonProperty("access_token")
    private String accessToken;
    private UserLoginResponse user;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserLoginResponse {
        private Long id;
        private String name;
        private String email;
        private Role role;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserGetAccount {
        private UserLoginResponse user;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInsideToken {
        private Long id;
        private String name;
        private String email;
    }
}
