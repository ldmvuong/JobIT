package fit.hcmute.jobit.service;

import fit.hcmute.jobit.dto.request.subcriber.CreateSubscriberRequest;
import fit.hcmute.jobit.dto.request.subcriber.UpdateSubscriberRequest;
import fit.hcmute.jobit.dto.response.subcriber.SubscriberResponse;

public interface SubscriberService {
    SubscriberResponse createSubscriber(CreateSubscriberRequest createSubscriberRequest);
    SubscriberResponse updateSubscriber(UpdateSubscriberRequest updateSubscriberRequest);
    void sendSubscribersEmailJobs();
    SubscriberResponse findByEmail(String email);

}
