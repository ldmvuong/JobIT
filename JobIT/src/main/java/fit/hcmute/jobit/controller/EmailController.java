package fit.hcmute.jobit.controller;

import fit.hcmute.jobit.service.impl.EmailService;
import fit.hcmute.jobit.util.annotation.ApiMessage;
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
