package fit.hcmute.jobit.controller;

import fit.hcmute.jobit.dto.request.subcriber.CreateSubscriberRequest;
import fit.hcmute.jobit.dto.request.subcriber.UpdateSubscriberRequest;
import fit.hcmute.jobit.dto.response.subcriber.SubscriberResponse;
import fit.hcmute.jobit.service.SubscriberService;
import fit.hcmute.jobit.util.SecurityUtil;
import fit.hcmute.jobit.util.annotation.ApiMessage;
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

        String email = SecurityUtil.getCurrentUserLogin().orElse("");

        return ResponseEntity
                .ok()
                .body(subscriberService.findByEmail(email));

    }
}
