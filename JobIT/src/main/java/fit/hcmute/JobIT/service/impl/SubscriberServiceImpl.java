package fit.hcmute.JobIT.service.impl;

import fit.hcmute.JobIT.converter.SubscriberMapper;
import fit.hcmute.JobIT.dto.request.IdRequest;
import fit.hcmute.JobIT.dto.request.subcriber.CreateSubscriberRequest;
import fit.hcmute.JobIT.dto.request.subcriber.UpdateSubscriberRequest;
import fit.hcmute.JobIT.dto.response.email.EmailJobResponse;
import fit.hcmute.JobIT.dto.response.subcriber.SubscriberResponse;
import fit.hcmute.JobIT.entity.Job;
import fit.hcmute.JobIT.entity.Skill;
import fit.hcmute.JobIT.entity.Subscriber;
import fit.hcmute.JobIT.exception.IdInvalidException;
import fit.hcmute.JobIT.repository.JobRepository;
import fit.hcmute.JobIT.repository.SkillRepository;
import fit.hcmute.JobIT.repository.SubcriberRepository;
import fit.hcmute.JobIT.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {
    private final SubcriberRepository subcriberRepository;
    private final SkillRepository skillRepository;
    private final SubscriberMapper subscriberMapper;
    private final JobRepository jobRepository;
    private final SubcriberRepository subscriberRepository;
    private final EmailService emailService;


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

        List<Long> skillIds = updateSubscriberRequest.getSkills()
                .stream()
                .map(IdRequest::getId)
                .toList();

        List<Skill> skills = skillRepository.findAllById(skillIds);

        Subscriber existingSubscriber = subscriber.get();
        existingSubscriber.setSkills(skills);

        return subscriberMapper.toSubscriberResponse(subcriberRepository.save(existingSubscriber));
    }

    public void sendSubscribersEmailJobs() {
        List<Subscriber> listSubs = this.subscriberRepository.findAll();
        if (!listSubs.isEmpty()) {
            for (Subscriber sub : listSubs) {
                List<Skill> listSkills = sub.getSkills();
                if (listSkills != null && !listSkills.isEmpty()) {
                    List<Job> listJobs = this.jobRepository.findBySkillsIn(listSkills);
                    if (listJobs != null && !listJobs.isEmpty()) {

                         List<EmailJobResponse> arr = listJobs.stream().map(
                                 this::convertJobToSendEmail).collect(Collectors.toList());

                        this.emailService.sendEmailFromTemplateSync(
                                sub.getEmail(),
                                "Cơ hội việc làm hot đang chờ đón bạn, khám phá ngay",
                                "job",
                                sub.getName(),
                                arr);
                    }
                }
            }
        }
    }

    @Override
    public SubscriberResponse findByEmail(String email) {
        Subscriber subscriber = subcriberRepository.findByEmail(email);
        if (subscriber == null) {
            throw new IdInvalidException("Subscriber with email: " + email + " does not exist");
        }
        return subscriberMapper.toSubscriberResponse(subscriber);
    }

    public EmailJobResponse convertJobToSendEmail(Job job) {
        EmailJobResponse res = new EmailJobResponse();
        res.setName(job.getName());
        res.setSalary(job.getSalary());
        res.setCompany(new EmailJobResponse.ComanyEmail(job.getCompany().getName()));
        List<Skill> skills = job.getSkills();
        List<EmailJobResponse.SkillEmail> s = skills.stream().map(skill -> new EmailJobResponse.SkillEmail(skill.getName()))
                .collect(Collectors.toList());
        res.setSkills(s);
        return res;
    }


}
