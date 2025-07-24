package fit.hcmute.jobit.converter;

import fit.hcmute.jobit.dto.request.subcriber.CreateSubscriberRequest;
import fit.hcmute.jobit.dto.response.subcriber.SubscriberResponse;
import fit.hcmute.jobit.entity.Subscriber;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriberMapper {

    @Mapping(target= "skills", ignore = true)
    Subscriber toSubscriber(CreateSubscriberRequest createSubscriberRequest);

    SubscriberResponse toSubscriberResponse(Subscriber subscriber);
}
