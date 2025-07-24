package fit.hcmute.jobit.dto.request.subcriber;

import fit.hcmute.jobit.dto.request.IdRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateSubscriberRequest {
    private String name;
    private String email;
    List<IdRequest> skills;
}
