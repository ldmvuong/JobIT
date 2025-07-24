package fit.hcmute.jobit.dto.response.user;

import fit.hcmute.jobit.dto.response.role.RoleSimpleResponse;
import fit.hcmute.jobit.enums.EGender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private EGender gender;
    private String address;
    private int age;
    private Instant createdAt;
    private Instant updatedAt;
    private CompanyUser company;

    private RoleSimpleResponse role;

    @Getter
    @Setter
    public static class CompanyUser {
        private Long id;
        private String name;
    }
}
