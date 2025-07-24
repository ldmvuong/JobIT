package fit.hcmute.jobit.dto.response.company;


import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class CompanyResponse {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String logo;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
}
