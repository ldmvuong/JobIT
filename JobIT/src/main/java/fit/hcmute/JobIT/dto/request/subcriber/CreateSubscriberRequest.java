package fit.hcmute.JobIT.dto.request.subcriber;

import fit.hcmute.JobIT.dto.request.IdRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateSubscriberRequest {
    private String name;
    private String email;
    List<IdRequest> skills;
}
