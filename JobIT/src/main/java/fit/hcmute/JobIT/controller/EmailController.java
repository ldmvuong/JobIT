package fit.hcmute.JobIT.controller;

import fit.hcmute.JobIT.service.impl.EmailService;
import fit.hcmute.JobIT.util.annotation.ApiMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/email")
    @ApiMessage("Sent simple email")
    public String sendEmail() {
       emailService.sentEmail();
       return "Email sent successfully!";
    }
}
