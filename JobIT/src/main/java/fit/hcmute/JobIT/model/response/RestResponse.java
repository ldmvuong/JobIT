package fit.hcmute.JobIT.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestResponse<T>  {
    private int statusCode;
    private String error;

    // Message can be a string or an object, depending on the context
    private Object message;
    private T data;
}
