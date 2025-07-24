package fit.hcmute.jobit.dto.response.user;

import fit.hcmute.jobit.enums.EGender;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UpdateUserResponse {
    private Long id;
    private String name;
    private EGender gender;
    private String address;
    private int age;
    private Instant updatedAt;
    private CompanyUser company;

    @Getter
    @Setter
    public static class CompanyUser {
        private Long id;
        private String name;
    }
}
