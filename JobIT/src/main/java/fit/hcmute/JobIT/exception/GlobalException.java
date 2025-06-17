package fit.hcmute.JobIT.exception;

import fit.hcmute.JobIT.model.response.RestResponse;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = IdInvalidException.class)
    public ResponseEntity<RestResponse<Object>> IdInvalidException(IdInvalidException e) {
        RestResponse<Object> response = new RestResponse<Object>();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setError(e.getMessage());
        response.setMessage("Invalid ID provided");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
