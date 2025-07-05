package fit.hcmute.JobIT.controller;

import fit.hcmute.JobIT.dto.request.subcriber.CreateSubscriberRequest;
import fit.hcmute.JobIT.dto.request.subcriber.UpdateSubscriberRequest;
import fit.hcmute.JobIT.dto.response.subcriber.SubscriberResponse;
import fit.hcmute.JobIT.service.SubscriberService;
import fit.hcmute.JobIT.util.SecurityUtil;
import fit.hcmute.JobIT.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscribers")
@RequiredArgsConstructor
public class SubscriberController {
    private final SubscriberService subscriberService;

    @PostMapping()
    @ApiMessage("Create a new subscriber")
    public ResponseEntity<SubscriberResponse> createSubscriber(@Valid @RequestBody CreateSubscriberRequest createSubscriberRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(subscriberService.createSubscriber(createSubscriberRequest));
    }

    @PutMapping
    @ApiMessage("Update an existing subscriber")
    public ResponseEntity<SubscriberResponse> updateSubscriber(@Valid @RequestBody UpdateSubscriberRequest updateSubscriberRequest) {
        return ResponseEntity
                .ok()
                .body(subscriberService.updateSubscriber(updateSubscriberRequest));
    }

    @PostMapping("/skills")
    @ApiMessage("Get all skills of a subscriber")
    public ResponseEntity<SubscriberResponse> getAllSubscribers() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent()
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";

        return ResponseEntity
                .ok()
                .body(subscriberService.findByEmail(email));

    }
}
