package fit.hcmute.JobIT.dto.response;

import fit.hcmute.JobIT.enums.EGender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {
   private Long id;
    private String name;
    private String email;
    private EGender gender;
    private String address;
    private int age;
    private Instant createdAt;
}
