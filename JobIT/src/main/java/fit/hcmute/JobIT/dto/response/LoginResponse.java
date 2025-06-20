package fit.hcmute.JobIT.dto.response;

import lombok.*;

@Getter
@Setter
public class LoginResponse {
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
    }
}
