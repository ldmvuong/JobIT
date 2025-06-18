package fit.hcmute.JobIT.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResponse<T> {
    private int statusCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    // Message can be a string or an object, depending on the context
    private Object message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}
