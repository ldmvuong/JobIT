package fit.hcmute.JobIT.service;

import fit.hcmute.JobIT.dto.request.subcriber.CreateSubscriberRequest;
import fit.hcmute.JobIT.dto.request.subcriber.UpdateSubscriberRequest;
import fit.hcmute.JobIT.dto.response.subcriber.SubscriberResponse;

public interface SubscriberService {
    SubscriberResponse createSubscriber(CreateSubscriberRequest createSubscriberRequest);
    SubscriberResponse updateSubscriber(UpdateSubscriberRequest updateSubscriberRequest);

}
