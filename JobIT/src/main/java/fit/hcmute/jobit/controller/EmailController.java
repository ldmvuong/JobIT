package fit.hcmute.jobit.controller;

import fit.hcmute.jobit.service.SubscriberService;
import fit.hcmute.jobit.util.annotation.ApiMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmailController {

    private final SubscriberService subscriberService;

    @GetMapping("/email")
    @ApiMessage("Sent simple email")
//    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public String sendEmail() {
       this.subscriberService.sendSubscribersEmailJobs();
       return "Email sent successfully!";
    }
}
