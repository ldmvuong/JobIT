package fit.hcmute.JobIT.service.impl;

import fit.hcmute.JobIT.converter.SubscriberMapper;
import fit.hcmute.JobIT.dto.request.IdRequest;
import fit.hcmute.JobIT.dto.request.subcriber.CreateSubscriberRequest;
import fit.hcmute.JobIT.dto.request.subcriber.UpdateSubscriberRequest;
import fit.hcmute.JobIT.dto.response.subcriber.SubscriberResponse;
import fit.hcmute.JobIT.entity.Skill;
import fit.hcmute.JobIT.entity.Subscriber;
import fit.hcmute.JobIT.exception.IdInvalidException;
import fit.hcmute.JobIT.repository.SkillRepository;
import fit.hcmute.JobIT.repository.SubcriberRepository;
import fit.hcmute.JobIT.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {
    private final SubcriberRepository subcriberRepository;
    private final SkillRepository skillRepository;
    private final SubscriberMapper subscriberMapper;

    @Override
    public SubscriberResponse createSubscriber(CreateSubscriberRequest createSubscriberRequest) {

        boolean exitsbyEmail = subcriberRepository.existsByEmail(createSubscriberRequest.getEmail());

        if (exitsbyEmail) {
            throw new IdInvalidException("Email:" + createSubscriberRequest.getEmail() + " already registered");
        }

        // Get skill IDs from the request and fetch corresponding Skill entities
        List<Long> skillIds = createSubscriberRequest.getSkills()
                .stream()
                .map(IdRequest::getId)
                .toList();
        List<Skill> skills = skillRepository.findAllById(skillIds);

        Subscriber subscriber = subscriberMapper.toSubscriber(createSubscriberRequest);
        subscriber.setSkills(skills);

        return subscriberMapper.toSubscriberResponse(subcriberRepository.save(subscriber));
    }

    @Override
    public SubscriberResponse updateSubscriber(UpdateSubscriberRequest updateSubscriberRequest) {
        Optional<Subscriber> subscriber = subcriberRepository.findById(updateSubscriberRequest.getId());

        if (subscriber.isEmpty()) {
            throw new IdInvalidException("Subscriber with ID: " + updateSubscriberRequest.getId() + " does not exist");
        }

        // Get skill IDs from the request and fetch corresponding Skill entities
        List<Long> skillIds = updateSubscriberRequest.getSkills()
                .stream()
                .map(IdRequest::getId)
                .toList();

        List<Skill> skills = skillRepository.findAllById(skillIds);

        Subscriber existingSubscriber = subscriber.get();
        existingSubscriber.setSkills(skills);

        return subscriberMapper.toSubscriberResponse(subcriberRepository.save(existingSubscriber));
    }
}
