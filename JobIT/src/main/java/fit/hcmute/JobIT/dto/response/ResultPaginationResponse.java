package fit.hcmute.JobIT.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultPaginationResponse {
    private Meta meta;
    private Object result;
}
