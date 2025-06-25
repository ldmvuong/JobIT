package fit.hcmute.JobIT.exception;

import fit.hcmute.JobIT.dto.response.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {

    // Xử lý các exception liên quan đến xác thực người dùng, id không hợp lệ
    @ExceptionHandler(value = {
            UsernameNotFoundException.class,
            BadCredentialsException.class,
            IdInvalidException.class
    })
    public ResponseEntity<RestResponse<Object>> handleIdException(Exception ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(ex.getMessage());
        res.setMessage("Exception occurs...");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    // Xử lý các exception liên quan đến truy cập không hợp lệ, không tìm thấy tài nguyên
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<RestResponse<Object>> handleNotFound(NoResourceFoundException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setError(ex.getMessage());
        res.setMessage("404 Not Found: URL may not exist...");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Object>> validationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();

        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());

        // Có thể custom thông báo tổng thể ở đây nếu muốn
        res.setError(ex.getBody().getDetail());

        List<String> errors = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        res.setMessage(errors.size() > 1 ? errors : errors.get(0));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    // Xử lý các exception liên quan đến kiểu dữ liệu không hợp lệ
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RestResponse<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError("Invalid parameter");

        String message = String.format("Parameter '%s' expects type '%s', but got value: '%s'",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "Unknown",
                ex.getValue());

        res.setMessage(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestResponse<Object>> handleEnumDeserialization(HttpMessageNotReadableException ex) {
        String message = ex.getMessage();

        if (message.contains("from String") && message.contains("Enum class")) {
            RestResponse<Object> res = new RestResponse<>();
            res.setStatusCode(HttpStatus.BAD_REQUEST.value());
            res.setError("Invalid enum value");

            // Lấy giá trị sai và các giá trị hợp lệ
            String invalidValue = message.split("from String")[1].split(":")[0].replace("\"", "").trim();
            String accepted = message.contains("accepted for Enum class") ?
                    message.substring(message.indexOf("[") + 1, message.indexOf("]")) :
                    "N/A";

            res.setMessage(String.format("'%s' không hợp lệ. Giá trị hợp lệ: [%s]", invalidValue, accepted));

            return ResponseEntity.badRequest().body(res);
        }

        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError("Invalid JSON or data format");
        res.setMessage(message);
        return ResponseEntity.badRequest().body(res);
    }

    // Xử lý các exception liên quan đến lỗi khác
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError("Invalid argument");
        res.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(value = {
           StorageException.class
    })
    public ResponseEntity<RestResponse<Object>> handleFileUploadException(Exception ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(ex.getMessage());
        res.setMessage("Exception upload file...");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
}
