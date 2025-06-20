package fit.hcmute.JobIT.dto.response;

import fit.hcmute.JobIT.enums.EGender;
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
}
