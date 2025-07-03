package fit.hcmute.JobIT.converter;

import fit.hcmute.JobIT.dto.request.subcriber.CreateSubscriberRequest;
import fit.hcmute.JobIT.dto.response.subcriber.SubscriberResponse;
import fit.hcmute.JobIT.entity.Subscriber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriberMapper {

    @Mapping(target= "skills", ignore = true)
    Subscriber toSubscriber(CreateSubscriberRequest createSubscriberRequest);

    SubscriberResponse toSubscriberResponse(Subscriber subscriber);
}
