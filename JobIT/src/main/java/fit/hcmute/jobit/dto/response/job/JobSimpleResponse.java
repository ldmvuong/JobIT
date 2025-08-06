package fit.hcmute.jobit.dto.response.job;

import fit.hcmute.jobit.dto.response.company.CompanyNameResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobSimpleResponse {
    private Long id;
    private String name;
//    private CompanyNameResponse company;
}
